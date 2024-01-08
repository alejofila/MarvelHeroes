package com.example.marvelchallenge.domain

import com.example.marvelchallenge.core.Either
import com.example.marvelchallenge.domain.model.Character
import com.example.marvelchallenge.domain.repository.CharactersRepository
import com.example.marvelchallenge.domain.repository.ComicRepository
import com.example.marvelchallenge.domain.repository.DomainError
import javax.inject.Inject
import com.example.marvelchallenge.core.flatMap
import com.example.marvelchallenge.core.toLeft
import com.example.marvelchallenge.domain.repository.EventsRepository
import com.example.marvelchallenge.domain.repository.SeriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetCharacterDetailsInteractor @Inject constructor(
  private val charactersRepository: CharactersRepository,
  private val comicRepository: ComicRepository,
  private val seriesRepository: SeriesRepository,
  private val eventsRepository: EventsRepository
) {

  suspend fun getCharacterInfo(characterId: Int): Either<DomainError, Character> {
    return charactersRepository.getCharacterById(characterId)
  }
  suspend fun getComicDetails(characterId: Int): Either<DomainError, Character> {
    return getCharacterInfo(characterId).flatMap { character ->
      comicRepository.getComicForCharacter(character).map { comics ->
        character.copy(comics = comics)
      }
    }

  }
  suspend fun getSeriesDetail(characterId: Int): Either<DomainError, Character> {
    return getCharacterInfo(characterId).flatMap { character ->
      seriesRepository.getSeriesForCharacter(character).map { series ->
        character.copy(series = series)
      }
    }

  }
  suspend fun getEventDetails(characterId: Int): Either<DomainError, Character> {
    return getCharacterInfo(characterId).flatMap { character ->
      eventsRepository.getEventsForCharacter(character).map { events ->
        character.copy(events = events)
      }
    }

  }
}