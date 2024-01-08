package com.example.marvelchallenge.domain

import com.example.marvelchallenge.core.Either
import com.example.marvelchallenge.domain.model.Character
import com.example.marvelchallenge.domain.repository.CharactersRepository
import com.example.marvelchallenge.domain.repository.DomainError
import javax.inject.Inject

class GetCharacterInfoInteractor @Inject constructor(
  private val charactersRepository: CharactersRepository
) {

  suspend operator fun invoke(characterId: kotlin.Int): Either<DomainError, Character> {
    return charactersRepository.getCharacterById(characterId)
  }
}