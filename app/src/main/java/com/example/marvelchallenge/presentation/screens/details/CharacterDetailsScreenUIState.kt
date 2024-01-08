package com.example.marvelchallenge.presentation.screens.details

import com.example.marvelchallenge.presentation.model.CharacterUIModel
import com.example.marvelchallenge.presentation.model.ComicUIModel
import com.example.marvelchallenge.presentation.model.EventUIModel
import com.example.marvelchallenge.presentation.model.SeriesUIModel

data class CharacterDetailsScreenUIState(
  val characterState: DetailsState<CharacterUIModel>,
  val comicState: DetailsState<ComicUIModel>,
  val seriesState: DetailsState<SeriesUIModel>,
  val eventState: DetailsState<EventUIModel>,
)

sealed class DetailsState<out T> {
  data class Success<T>(val data: List<T>) : DetailsState<T>()
  data class Error(val message: String) : DetailsState<Nothing>()
  object Loading : DetailsState<Nothing>()
}



