package com.example.marvelchallenge.domain.repository

import com.example.marvelchallenge.core.Either
import com.example.marvelchallenge.domain.model.Character

interface CharactersRepository {
  suspend fun getCharacters(searchQuery: String, page: Int): Either<DomainError, List<Character>>

  suspend fun getCharacterById(id: Int): Either<DomainError, Character>
}