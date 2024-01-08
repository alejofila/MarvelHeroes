package com.example.marvelchallenge.domain

import com.example.marvelchallenge.core.Either
import com.example.marvelchallenge.domain.model.Character
import com.example.marvelchallenge.domain.repository.CharactersRepository
import com.example.marvelchallenge.domain.repository.DomainError
import javax.inject.Inject

class GetCharactersInformationInteractor @Inject constructor(
  private val charactersRepository: CharactersRepository,
) {
  suspend operator fun invoke(query: String, page: kotlin.Int): Either<DomainError, List<Character>> =
    charactersRepository.getCharacters(query, page)
}

