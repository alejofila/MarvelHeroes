package com.example.marvelchallenge.data.repository

import com.example.marvelchallenge.core.Either
import com.example.marvelchallenge.core.toRight
import com.example.marvelchallenge.data.mapper.BaseDetailsDTOMapper
import com.example.marvelchallenge.data.mapper.DomainErrorMapper
import com.example.marvelchallenge.data.remote.datasource.BaseRemoteDataSource
import com.example.marvelchallenge.domain.repository.DomainError
import com.example.marvelchallenge.domain.repository.Repository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

abstract class BaseRepositoryImpl<DomainModel, DataModel>(
  private val coroutineDispatcher: CoroutineDispatcher,
  private val domainErrorMapper: DomainErrorMapper,
  private val remoteDataSource: BaseRemoteDataSource<DataModel>,
  private val mapper: (DataModel) -> DomainModel
) : Repository<DomainModel> {

  private val inMemoryCache: MutableMap<Int, List<DomainModel>> = mutableMapOf()

  override suspend fun getDataForCharacter(characterId: Int): Either<DomainError, List<DomainModel>> {
    val cachedData = inMemoryCache[characterId]

    return if (cachedData.isNullOrEmpty()) {
      val result = withContext(coroutineDispatcher) {
        remoteDataSource.getItemsForCharacter(characterId)
      }
      result.mapLeft { mapError -> domainErrorMapper.map(mapError) }.map { data ->
        data.map { remoteData ->
          mapper(remoteData)
        }.also {
          saveData(characterId, it)
        }
      }
    } else {
      cachedData.toRight()
    }
  }

  private fun saveData(characterId: Int, data: List<DomainModel>) {
    inMemoryCache[characterId] = data
  }
}