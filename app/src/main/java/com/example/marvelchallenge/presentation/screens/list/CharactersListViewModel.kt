package com.example.marvelchallenge.presentation.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvelchallenge.domain.GetCharactersInteractor
import com.example.marvelchallenge.domain.model.Character
import com.example.marvelchallenge.domain.repository.DomainError
import com.example.marvelchallenge.presentation.mapper.CharacterMapperUIModel
import com.example.marvelchallenge.presentation.mapper.CharacterListUIErrorMapper
import com.example.marvelchallenge.presentation.screens.list.CharacterListState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class CharactersListViewModel @Inject constructor(
  private val mapper: CharacterMapperUIModel,
  private val errorMapper: CharacterListUIErrorMapper,
  private val getCharactersInteractor: GetCharactersInteractor
) : ViewModel() {
  private var pageCount = 0
  private val _characterScreenUIState = MutableStateFlow(
    CharacterScreenUIState(
      characterListState = Success(emptyList()), searchQuery = "", isLoading = false
    )
  )
  val characterScreenUIState = _characterScreenUIState.asStateFlow()

  init {
    queryCharacters("")
  }

  fun queryCharacters(queryString: String) {
    _characterScreenUIState.update { oldState ->
      oldState.copy(
        searchQuery = queryString
      )
    }
    resetPageCount()
    query()
  }

  private fun resetPageCount() {
    pageCount = 0
  }

  fun queryNextPage() {
    increasePageCount()
    query()
  }

  private fun increasePageCount() {
    pageCount++
  }

  @OptIn(FlowPreview::class)
  private fun query() {
    viewModelScope.launch {
      _characterScreenUIState.update { oldState ->
        oldState.copy(
          isLoading = true
        )
      }
      _characterScreenUIState.debounce(SEARCH_QUERY_DEBOUNCE_TIME).collectLatest { state ->
        getCharactersInteractor(state.searchQuery, pageCount).fold(
          ifLeft = { domainError ->
            handleError(domainError)
          },
          ifRight = { characters ->
            handleResult(characters)
          })
      }
    }
  }

  private fun handleResult(characters: List<Character>) {
    val characterUIModelList = characters.map {
      mapper.mapToUIModel(it)
    }
    if (characterUIModelList.isEmpty()) {
      _characterScreenUIState.update { oldState ->
        oldState.copy(
          isLoading = false
        )
      }
    } else {
      _characterScreenUIState.update { oldState ->
        oldState.copy(
          characterListState = Success(characterUIModelList),
          isLoading = false
        )
      }
    }
  }

  private fun handleError(domainError: DomainError) {
    errorMapper.mapToUIError(domainError).let { uiError ->
      _characterScreenUIState.update { oldState ->
        oldState.copy(
          characterListState = uiError,
          isLoading = false
        )
      }
    }
  }

  companion object {
    private const val SEARCH_QUERY_DEBOUNCE_TIME = 1000L
  }
}
