package com.example.marvelchallenge.data.repository

import com.example.marvelchallenge.core.Either
import com.example.marvelchallenge.core.toRight
import com.example.marvelchallenge.data.mapper.DomainErrorMapper
import com.example.marvelchallenge.data.mapper.EventDetailsDTOMapper
import com.example.marvelchallenge.data.remote.datasource.EventsRemoteDataSource
import com.example.marvelchallenge.data.remote.model.EventResult
import com.example.marvelchallenge.domain.model.Character
import com.example.marvelchallenge.domain.model.Event
import com.example.marvelchallenge.domain.model.EventDetails
import com.example.marvelchallenge.domain.repository.DomainError
import com.example.marvelchallenge.domain.repository.EventsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventsRepositoryImpl @Inject constructor(
  private val coroutineDispatcher: CoroutineDispatcher,
  private val mapper: EventDetailsDTOMapper,
  private val domainErrorMapper: DomainErrorMapper,
  private val remoteDataSource: EventsRemoteDataSource
) :
  EventsRepository {
  private val inMemoryCache = mutableMapOf<Int, Event>()

  override suspend fun getEventsForCharacter(character: Character): Either<DomainError, List<Event>> {

    return if (character.events.any { event -> event.details == null }) {
      val result = withContext(coroutineDispatcher) {
        remoteDataSource.getEventsForCharacter(character.id)
      }
      result.mapLeft { mapError -> domainErrorMapper.map(mapError) }.map { comics ->
        comics.map { remoteComic ->
          mapEvent(remoteComic)
        }
      }
    } else {
      character.events.toRight()
    }
  }

  private fun mapEvent(remoteEvent: EventResult): Event {
    val eventDetails = mapper.mapToDomain(remoteEvent)
    return Event(
      id = remoteEvent.id,
      name = remoteEvent.title,
      details = eventDetails
    )
  }

  override suspend fun saveEvents(events: List<Event>) {
    events.forEach { event ->
      inMemoryCache[event.id] = event
    }
  }

  override suspend fun saveEventDetails(id: Int, event: EventDetails) {
    inMemoryCache[id]?.let { cachedEvent ->
      val updatedEvent = cachedEvent.copy(details = event)
      inMemoryCache[id] = updatedEvent
    }
  }
}