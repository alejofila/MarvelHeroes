package com.example.marvelchallenge.presentation.screens.list

import com.example.marvelchallenge.presentation.model.CharacterUIModel

data class CharacterScreenUIState(
  val characterListState: CharacterListState,
  val searchQuery: String,
  val isLoading: Boolean
)

sealed class CharacterListState {
  data class Success(val characters: List<CharacterUIModel>) : CharacterListState()
  data class Error(val message: String) : CharacterListState()
}
