package com.example.marvelchallenge.domain.repository

import com.example.marvelchallenge.core.Either
import com.example.marvelchallenge.domain.model.Character
import com.example.marvelchallenge.domain.model.Series
import com.example.marvelchallenge.domain.model.SeriesDetails

interface SeriesRepository {

  suspend fun getSeriesForCharacter(character: Character): Either<DomainError, List<Series>>

  suspend fun saveSeries(seriesList: List<Series>)

  suspend fun saveSeriesDetails(id: Int, series: SeriesDetails)
}