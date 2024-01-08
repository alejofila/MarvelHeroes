package com.example.marvelchallenge.domain

import com.example.marvelchallenge.core.Either
import com.example.marvelchallenge.domain.model.Character
import com.example.marvelchallenge.domain.model.Series
import com.example.marvelchallenge.domain.repository.DomainError
import com.example.marvelchallenge.domain.repository.SeriesRepository
import javax.inject.Inject

class GetSeriesForCharacterInteractor @Inject constructor(
  private val seriesRepository: SeriesRepository
) {

  suspend operator fun invoke(character: Int): Either<DomainError, List<Series>> {
    return seriesRepository.getDataForCharacter(character)
  }
}