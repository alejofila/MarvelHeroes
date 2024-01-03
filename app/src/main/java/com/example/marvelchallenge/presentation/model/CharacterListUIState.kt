package com.example.marvelchallenge.presentation.model

sealed class CharacterListUIState {
    object Loading : CharacterListUIState()
    data class Success(val characters: List<CharacterUIModel>) : CharacterListUIState()
    data class Error(val message: String) : CharacterListUIState()
    object EmptyScreen : CharacterListUIState()
}
