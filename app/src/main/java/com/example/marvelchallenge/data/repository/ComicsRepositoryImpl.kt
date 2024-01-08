package com.example.marvelchallenge.data.repository

import com.example.marvelchallenge.data.mapper.ComicDTOMapper
import com.example.marvelchallenge.data.mapper.DomainErrorMapper
import com.example.marvelchallenge.data.remote.datasource.ComicsRemoteDataSource
import com.example.marvelchallenge.data.remote.model.ComicDataItem
import com.example.marvelchallenge.domain.model.Comic
import com.example.marvelchallenge.domain.repository.ComicRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ComicsRepositoryImpl @Inject constructor(
  coroutineDispatcher: CoroutineDispatcher,
  domainErrorMapper: DomainErrorMapper,
  comicsRemoteDataSource: ComicsRemoteDataSource,
  private val mapper: ComicDTOMapper
) : ComicRepository, BaseRepositoryImpl<Comic, ComicDataItem>(
  coroutineDispatcher,
  domainErrorMapper,
  comicsRemoteDataSource,
  mapper::mapDetailsToDomain
) {

  init {

    println("INIT ComicsRepositoryImpl ${hashCode()}")
  }
}