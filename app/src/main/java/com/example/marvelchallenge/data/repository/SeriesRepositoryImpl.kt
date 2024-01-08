package com.example.marvelchallenge.data.repository

import com.example.marvelchallenge.data.mapper.DomainErrorMapper
import com.example.marvelchallenge.data.mapper.SeriesDetailsDTOMapper
import com.example.marvelchallenge.data.remote.datasource.SeriesRemoteDataSource
import com.example.marvelchallenge.data.remote.model.SeriesDataItem
import com.example.marvelchallenge.domain.model.Series
import com.example.marvelchallenge.domain.repository.SeriesRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SeriesRepositoryImpl @Inject constructor(
  coroutineDispatcher: CoroutineDispatcher,
  domainErrorMapper: DomainErrorMapper,
  seriesRemoteDataSource: SeriesRemoteDataSource,
  private val mapper: SeriesDetailsDTOMapper
) : SeriesRepository,BaseRepositoryImpl<Series, SeriesDataItem>(
  coroutineDispatcher,
  domainErrorMapper,
  seriesRemoteDataSource,
  mapper::mapDetailsToDomain
)