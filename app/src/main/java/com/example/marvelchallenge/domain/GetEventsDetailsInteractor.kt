package com.example.marvelchallenge.domain

import com.example.marvelchallenge.core.Either
import com.example.marvelchallenge.domain.model.Character
import com.example.marvelchallenge.domain.model.Event
import com.example.marvelchallenge.domain.repository.DomainError
import com.example.marvelchallenge.domain.repository.EventsRepository
import javax.inject.Inject

class GetEventsForCharacterInteractor @Inject constructor(
  private val eventsRepository: EventsRepository
) {

  suspend operator fun invoke(characterId: Int): Either<DomainError, List<Event>> {
    return eventsRepository.getDataForCharacter(characterId)
  }
}