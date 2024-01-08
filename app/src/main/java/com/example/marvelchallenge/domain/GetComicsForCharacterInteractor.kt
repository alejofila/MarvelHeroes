package com.example.marvelchallenge.domain

import com.example.marvelchallenge.core.Either
import com.example.marvelchallenge.domain.model.Character
import com.example.marvelchallenge.domain.model.Comic
import com.example.marvelchallenge.domain.repository.ComicRepository
import com.example.marvelchallenge.domain.repository.DomainError
import javax.inject.Inject

class GetComicsForCharacterInteractor @Inject constructor(
  private val comicRepository: ComicRepository
) {

  suspend operator fun invoke(characterId: Int): Either<DomainError, List<Comic>> {
    return comicRepository.getDataForCharacter(characterId)
  }
}