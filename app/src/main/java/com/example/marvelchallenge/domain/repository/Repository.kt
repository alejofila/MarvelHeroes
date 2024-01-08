package com.example.marvelchallenge.domain.repository

import com.example.marvelchallenge.core.Either
import com.example.marvelchallenge.domain.model.Comic
import com.example.marvelchallenge.domain.model.Event
import com.example.marvelchallenge.domain.model.Series

interface Repository<T> {
  suspend fun getDataForCharacter(characterId: Int): Either<DomainError, List<T>>
}

interface ComicRepository : Repository<Comic>
interface SeriesRepository : Repository<Series>
interface EventsRepository : Repository<Event>