package com.example.marvelchallenge.data.repository

import com.example.marvelchallenge.core.Either
import com.example.marvelchallenge.core.toLeft
import com.example.marvelchallenge.core.toRight
import com.example.marvelchallenge.data.mapper.CharacterDTOMapper
import com.example.marvelchallenge.data.mapper.DomainErrorMapper
import com.example.marvelchallenge.data.remote.datasource.CharactersRemoteDataSource
import com.example.marvelchallenge.data.remote.PAGE_LIMIT
import com.example.marvelchallenge.domain.model.Character
import com.example.marvelchallenge.domain.repository.CharactersRepository
import com.example.marvelchallenge.domain.repository.DomainError
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharactersRepositoryImpl @Inject constructor(
  private val coroutineDispatcher: CoroutineDispatcher,
  private val mapper: CharacterDTOMapper,
  private val domainErrorMapper: DomainErrorMapper,
  private val remoteDataSource: CharactersRemoteDataSource
) :
  CharactersRepository {
  private val inMemoryCache = mutableMapOf<Int, Character>()

  override suspend fun getCharacters(
    searchQuery: String,
    page: Int
  ): Either<DomainError, List<Character>> {
    var currentOffset = page * PAGE_LIMIT
    val charactersMatchingCriteria =
      getCharactersMatchingCriteria(searchQuery)

    if (charactersMatchingCriteria.isEmpty() || currentOffset >= charactersMatchingCriteria.size) {
      val result = withContext(coroutineDispatcher) {
        remoteDataSource.getCharacters(
          query = searchQuery.takeIf { it.isNotEmpty() },
          offset = currentOffset
        ).map {
          it.map { remoteCharacterDTO ->
            mapper.mapToDomain(remoteCharacterDTO).also { remoteCharacter ->
              inMemoryCache[remoteCharacter.id] = remoteCharacter
            }
          }
          currentOffset += PAGE_LIMIT
        }
      }

      return result.mapLeft { mapError -> domainErrorMapper.map(mapError) }.map {
        getCharactersMatchingCriteria(searchQuery)
          .take(currentOffset)
      }

    } else {
      return getCharactersMatchingCriteria(searchQuery)
        .take(currentOffset)
        .toRight()
    }

  }

  override suspend fun getCharacterById(id: Int): Either<DomainError, Character> {
    return inMemoryCache[id]?.toRight()
      ?: DomainError.UnknownError("No character found with id $id").toLeft()
  }

  private fun getCharactersMatchingCriteria(searchQuery: String) =
    inMemoryCache.values.filter { it.name.contains(searchQuery, ignoreCase = true) }
}