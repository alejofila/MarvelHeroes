package com.example.marvelchallenge.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.marvelchallenge.presentation.components.SearchAppBar
import com.example.marvelchallenge.presentation.model.CharacterListUIState
import com.example.marvelchallenge.presentation.model.CharacterListUIState.EmptyScreen
import com.example.marvelchallenge.presentation.model.CharacterListUIState.Error
import com.example.marvelchallenge.presentation.model.CharacterListUIState.Loading
import com.example.marvelchallenge.presentation.model.CharacterListUIState.Success
import com.example.marvelchallenge.presentation.model.CharacterUIModel

@Composable
fun CharacterListScreen(charactersState: CharacterListUIState, onTypedText: (String) -> Unit) {
  CharactersList(charactersState, onTypedText)

}

@Composable private fun LoadingComposable() {
  Column(verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
    CircularProgressIndicator(
      modifier = Modifier
        .size(180.dp)
        .align(Alignment.CenterHorizontally),
      strokeWidth = 8.dp,
      color = MaterialTheme.colorScheme.primary
    )
  }

}

@Composable private fun ErrorComposable(errorMessage: String) {
  Column(verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
    Text(text = errorMessage, modifier = Modifier.align(Alignment.CenterHorizontally))
  }
}

@Composable
private fun CharactersList(state: CharacterListUIState, typedText: (String) -> Unit) {
  var searchQuery by remember { mutableStateOf("") }
  Column(
    modifier = Modifier
      .fillMaxSize()
  ) {
    SearchAppBar(
      text = searchQuery,
      onTextChange = {
        searchQuery = it
        typedText(it)
      },
      onSearchClicked = {},
    )
    Spacer(modifier = Modifier.height(16.dp))
    when (state) {
      is Error -> ErrorComposable(state.message)
      Loading -> LoadingComposable()
      is Success -> {
        CharacterList(state.characters)
      }

      EmptyScreen -> EmptyList()
    }
  }
}

@Composable
private fun CharacterList(characters: List<CharacterUIModel>) {
  LazyColumn {
    items(characters) { character ->
      CharacterItem(character = character)
    }
  }
}

@Composable
private fun EmptyList() {
  Text(text = "There are no characters")
}

@Composable
fun CharacterItem(character: CharacterUIModel) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .clickable { /* Handle item click, navigate to Details View */ }
      .padding(8.dp),
  ) {
    AsyncImage(
      model = character.thumbnailUrl,
      contentDescription = "Character ${character.name} Thumbnail",
      contentScale = ContentScale.Crop,
      modifier = Modifier
        .height(120.dp)
        .fillMaxWidth()
        .clip(MaterialTheme.shapes.medium),

      onError = { println("Error trying to load ${character.thumbnailUrl} ${it.result.throwable.message}") },
      onSuccess = { println("${character.thumbnailUrl} loaded successfully ") },
      onLoading = { println("Trying to load ${character.thumbnailUrl}") },
    )

    Spacer(modifier = Modifier.height(8.dp))

    Text(
      text = character.name,
      style = MaterialTheme.typography.headlineSmall,
      modifier = Modifier.align(Alignment.CenterHorizontally),
    )
  }
}
