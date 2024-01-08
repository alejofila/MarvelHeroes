package com.example.marvelchallenge.domain.repository

import com.example.marvelchallenge.core.Either
import com.example.marvelchallenge.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface ReactiveCharactersRepository {
  suspend fun getCharacters(
    searchQuery: String,
    page: Int
  ): Flow<Either<DomainError, List<Character>>>

}