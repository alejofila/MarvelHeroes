package com.example.marvelchallenge.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvelchallenge.presentation.base.StateHolder
import com.example.marvelchallenge.presentation.base.StateHolderImpl
import com.example.marvelchallenge.presentation.model.CharacterListUIState
import com.example.marvelchallenge.presentation.model.CharacterUIModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CharactersListViewModel :
  ViewModel(),
  StateHolder<CharacterListUIState> by StateHolderImpl(CharacterListUIState.Loading) {

  init {
    setState { CharacterListUIState.Loading }

    viewModelScope.launch {
      delay(1500)
      setState { CharacterListUIState.Success(stockData) }
    }
  }

  fun queryCharacters(queryString: String = "") {
    viewModelScope.launch {
      setState { CharacterListUIState.Loading }
      delay(1500)
      val filteredCharacters = stockData.filter {
        it.name.contains(queryString, ignoreCase = true)
      }
      if (filteredCharacters.isEmpty()) {
        setState { CharacterListUIState.EmptyScreen }
      } else {
        setState { CharacterListUIState.Success(filteredCharacters) }
      }
    }
  }
}

private val stockData = listOf(
  CharacterUIModel(
    1,
    "Iron Man",
    "https://i.annihil.us/u/prod/marvel/i/mg/c/60/55b6a28ef24fa.jpg",
  ),
  CharacterUIModel(2, "Thor", "https://i.annihil.us/u/prod/marvel/i/mg/d/d0/5269657a74350.jpg"),
  CharacterUIModel(
    3,
    "Black Widow",
    "https://i.annihil.us/u/prod/marvel/i/mg/f/30/50fecad1f395b.jpg",
  ),
  CharacterUIModel(
    4,
    "Captain America",
    "https://i.annihil.us/u/prod/marvel/i/mg/9/80/537ba5b368b7d.jpg",
  ),
  CharacterUIModel(5, "Hulk", "https://i.annihil.us/u/prod/marvel/i/mg/5/a0/538615ca33ab0.jpg"),
  CharacterUIModel(
    6,
    "Spider-Man",
    "https://i.annihil.us/u/prod/marvel/i/mg/3/50/526548a343e4b.jpg",
  ),
  CharacterUIModel(
    7,
    "Doctor Strange",
    "https://i.annihil.us/u/prod/marvel/i/mg/5/f0/5261a85a501fe.jpg",
  ),
  CharacterUIModel(
    8,
    "Black Panther",
    "https://i.annihil.us/u/prod/marvel/i/mg/6/60/5261a80a67e7d.jpg",
  ),
  CharacterUIModel(
    9,
    "Scarlet Witch",
    "https://i.annihil.us/u/prod/marvel/i/mg/6/70/5261a6748f4d4.jpg",
  ),
  CharacterUIModel(
    10,
    "Wolverine",
    "https://i.annihil.us/u/prod/marvel/i/mg/9/00/537bcb1133fd7.jpg",
  ),
)
