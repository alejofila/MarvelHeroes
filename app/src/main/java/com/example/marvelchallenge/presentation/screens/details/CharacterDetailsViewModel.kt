package com.example.marvelchallenge.presentation.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvelchallenge.domain.GetCharacterInfoInteractor
import com.example.marvelchallenge.domain.GetComicsForCharacterInteractor
import com.example.marvelchallenge.domain.GetEventsForCharacterInteractor
import com.example.marvelchallenge.domain.GetSeriesForCharacterInteractor
import com.example.marvelchallenge.domain.model.Character
import com.example.marvelchallenge.presentation.mapper.CharacterMapperUIModel
import com.example.marvelchallenge.presentation.mapper.ComicMapperUIModel
import com.example.marvelchallenge.presentation.mapper.EventMapperUIModel
import com.example.marvelchallenge.presentation.mapper.SeriesMapperUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
  private val getCharacterDetailsInteractor: GetCharacterInfoInteractor,
  private val getSeriesForCharacterInteractor: GetSeriesForCharacterInteractor,
  private val getComicsForCharacterInteractor: GetComicsForCharacterInteractor,
  private val getEventsForCharacterInteractor: GetEventsForCharacterInteractor,
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

  private suspend fun fetchCharacter(characterId: Int) {
    getCharacterDetailsInteractor(characterId).fold(
      ifLeft = { error ->
        _characterDetailState.update { old ->
          old.copy(
            characterState = DetailsState.Error("Error fetching character")
          )
        }
      },
      ifRight = { character ->
        _characterDetailState.update { old ->
          old.copy(
            characterState = DetailsState.Success(
              listOf(characterMapperUIModel.mapToUIModel(character))
            )
          )
        }
      })
  }

  private suspend fun fetchEvents(characterId: Int) {
    getEventsForCharacterInteractor(characterId).fold(
      ifLeft = { error ->
        _characterDetailState.update { old ->
          old.copy(
            eventState = DetailsState.Error("Error fetching events")
          )
        }
      },
      ifRight = { result ->
        _characterDetailState.update { old ->
          old.copy(
            eventState = DetailsState.Success(result.map { event ->
              eventMapperUIModel.mapToUIModel(event)
            })
          )
        }
      }
    )

  }

  private suspend fun fetchSeries(characterId: Int) {
    getSeriesForCharacterInteractor(characterId).fold(
      ifLeft = { error ->
        _characterDetailState.update { old ->
          old.copy(
            seriesState = DetailsState.Error("Error fetching series")
          )
        }
      },
      ifRight = { result ->
        _characterDetailState.update { old ->
          old.copy(
            seriesState = DetailsState.Success(result.map { series ->
              seriesMapperUIModel.mapToUIModel(series)
            })
          )
        }
      }
    )
  }

  private suspend fun fetchComics(characterId: Int) {
    getComicsForCharacterInteractor(characterId).fold(
      ifLeft = { error ->
        _characterDetailState.update { old ->
          old.copy(
            comicState = DetailsState.Error("Error fetching comics")
          )
        }
      },
      ifRight = { result ->
        _characterDetailState.update { old ->
          old.copy(
            comicState = DetailsState.Success(result.map { comic ->
              comicMapperUIModel.mapToUIModel(comic)
            })
          )
        }
      }
    )
  }

}