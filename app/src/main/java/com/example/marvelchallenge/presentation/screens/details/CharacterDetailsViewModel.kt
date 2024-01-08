package com.example.marvelchallenge.presentation.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvelchallenge.domain.GetCharacterDetailsInteractor
import com.example.marvelchallenge.presentation.mapper.CharacterMapperUIModel
import com.example.marvelchallenge.presentation.mapper.ComicMapperUIModel
import com.example.marvelchallenge.presentation.mapper.EventMapperUIModel
import com.example.marvelchallenge.presentation.mapper.SeriesMapperUIModel
import com.example.marvelchallenge.presentation.model.CharacterUIModel
import com.example.marvelchallenge.presentation.model.ComicUIModel
import com.example.marvelchallenge.presentation.model.EventUIModel
import com.example.marvelchallenge.presentation.model.SeriesUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
  private val getCharacterDetailsInteractor: GetCharacterDetailsInteractor,
  private val characterMapperUIModel: CharacterMapperUIModel,
  private val comicMapperUIModel: ComicMapperUIModel,
  private val seriesMapperUIModel: SeriesMapperUIModel,
  private val eventMapperUIModel: EventMapperUIModel
) :
  ViewModel() {

  private val _characterDetailState = MutableStateFlow(
    CharacterDetailsScreenUIState(
      DetailsState.Loading,
      DetailsState.Loading,
      DetailsState.Loading,
      DetailsState.Loading
    )
  )

  val characterDetailState = _characterDetailState.asStateFlow()
  fun fetchCharacterDetails(characterId: Int) {
    viewModelScope.launch {
      fetchCharacter(characterId)
      fetchComics(characterId)
      fetchSeries(characterId)
      fetchEvents(characterId)
    }
  }

  private suspend fun fetchEvents(characterId: Int) {
    getCharacterDetailsInteractor.getEventDetails(characterId).fold(
      ifLeft = { error ->
        _characterDetailState.update { old ->
          old.copy(
            seriesState = DetailsState.Error("Error fetching events")
          )
        }
      },
      ifRight = { character ->
        _characterDetailState.update { old ->
          old.copy(
            eventState = DetailsState.Success(character.events.map { event ->
              eventMapperUIModel.mapToUIModel(event)
            })
          )
        }
      }
    )
  }

  private suspend fun fetchComics(characterId: Int) {
    getCharacterDetailsInteractor.getComicDetails(characterId).fold(
      ifLeft = { error ->
        _characterDetailState.update { old ->
          old.copy(
            seriesState = DetailsState.Error("Error fetching comics")
          )
        }
      },
      ifRight = { character ->
        _characterDetailState.update { old ->
          old.copy(
            comicState = DetailsState.Success(character.comics.map { comic ->
              println("DetailViewModel Series: pre ${comic.details}")
              comicMapperUIModel.mapToUIModel(comic).also {
                println("DetailViewModel Comic: $it")
              }
            })
          )
        }
      }
    )
  }

  private suspend fun fetchSeries(characterId: Int) {
    getCharacterDetailsInteractor.getSeriesDetail(characterId).fold(
      ifLeft = { error ->
        _characterDetailState.update { old ->
          old.copy(
            seriesState = DetailsState.Error("Error fetching series")
          )
        }
      },
      ifRight = { character ->
        _characterDetailState.update { old ->
          old.copy(
            seriesState = DetailsState.Success(character.series.map { series ->
              println("DetailViewModel Series: pre ${series.details}")
              seriesMapperUIModel.mapToUIModel(series).also {
                println("DetailViewModel Series: $it")
              }
            })
          )
        }
      }
    )
  }

  private suspend fun fetchCharacter(characterId: Int) {
    getCharacterDetailsInteractor.getCharacterInfo(characterId).fold(
      ifLeft = { error ->
        println("Error trying to retrieve character details: $error")
      },
      ifRight = { character ->
        _characterDetailState.update { old ->
          old.copy(
            characterState = DetailsState.Success(
              listOf(
                characterMapperUIModel.mapToUIModel(
                  character
                )
              )
            )
          )
        }
      }
    )
  }
}