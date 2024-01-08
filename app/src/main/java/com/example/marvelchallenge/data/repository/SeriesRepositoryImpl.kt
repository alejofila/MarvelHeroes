package com.example.marvelchallenge.data.repository

import com.example.marvelchallenge.core.Either
import com.example.marvelchallenge.core.toRight
import com.example.marvelchallenge.data.mapper.DomainErrorMapper
import com.example.marvelchallenge.data.mapper.SeriesDetailsDTOMapper
import com.example.marvelchallenge.data.remote.datasource.SeriesRemoteDataSource
import com.example.marvelchallenge.data.remote.model.SeriesResult
import com.example.marvelchallenge.domain.model.Character
import com.example.marvelchallenge.domain.model.Series
import com.example.marvelchallenge.domain.model.SeriesDetails
import com.example.marvelchallenge.domain.repository.DomainError
import com.example.marvelchallenge.domain.repository.SeriesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SeriesRepositoryImpl @Inject constructor(
  private val coroutineDispatcher: CoroutineDispatcher,
  private val mapper: SeriesDetailsDTOMapper,
  private val domainErrorMapper: DomainErrorMapper,
  private val remoteDataSource: SeriesRemoteDataSource
) : SeriesRepository {
  private val inMemoryCache = mutableMapOf<Int, Series>()

  override suspend fun getSeriesForCharacter(character: Character): Either<DomainError, List<Series>> {

    return if (character.series.any { series -> series.details == null }) {
      val result = withContext(coroutineDispatcher) {
        remoteDataSource.getSeriesForCharacter(character.id)
      }
      result.mapLeft { mapError -> domainErrorMapper.map(mapError) }.map { series ->
        series.map { remoteSeries ->
          mapSeries(remoteSeries)
        }
      }
    } else {
      character.series.toRight()
    }
  }

  private fun mapSeries(remoteSeries: SeriesResult): Series {
    val seriesDetails = mapper.mapToDomain(remoteSeries)
    return Series(
      id = remoteSeries.id,
      name = remoteSeries.title,
      details = seriesDetails
    )
  }

  override suspend fun saveSeries(seriesList: List<Series>) {
    seriesList.forEach { series ->
      inMemoryCache[series.id] = series
    }
  }

  override suspend fun saveSeriesDetails(id: Int, series: SeriesDetails) {
    inMemoryCache[id]?.let { cachedSeries ->
      inMemoryCache[id] = cachedSeries.copy(details = series)
    }
  }
}