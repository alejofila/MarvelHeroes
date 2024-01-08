package com.example.marvelchallenge.domain.repository

import com.example.marvelchallenge.core.Either
import com.example.marvelchallenge.domain.model.Character
import com.example.marvelchallenge.domain.model.Comic
import com.example.marvelchallenge.domain.model.ComicDetails
import com.example.marvelchallenge.domain.model.Event
import com.example.marvelchallenge.domain.model.EventDetails

interface EventsRepository {

  suspend fun getEventsForCharacter(character: Character): Either<DomainError, List<Event>>

  suspend fun saveEvents(events: List<Event>)

  suspend fun saveEventDetails(id: Int, event: EventDetails)
}