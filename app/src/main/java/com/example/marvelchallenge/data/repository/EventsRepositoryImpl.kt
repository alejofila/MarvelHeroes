package com.example.marvelchallenge.data.repository

import com.example.marvelchallenge.data.mapper.DomainErrorMapper
import com.example.marvelchallenge.data.mapper.EventDTOMapper
import com.example.marvelchallenge.data.remote.datasource.EventsRemoteDataSource
import com.example.marvelchallenge.data.remote.model.EventDataItem
import com.example.marvelchallenge.domain.model.Event
import com.example.marvelchallenge.domain.repository.EventsRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@javax.inject.Singleton
class EventsRepositoryImpl @Inject constructor(
  coroutineDispatcher: CoroutineDispatcher,
  domainErrorMapper: DomainErrorMapper,
  eventsRemoteDataSource: EventsRemoteDataSource,
  private val mapper: EventDTOMapper
) : EventsRepository, BaseRepositoryImpl<Event, EventDataItem>(
  coroutineDispatcher,
  domainErrorMapper,
  eventsRemoteDataSource,
  mapper::mapDetailsToDomain
)