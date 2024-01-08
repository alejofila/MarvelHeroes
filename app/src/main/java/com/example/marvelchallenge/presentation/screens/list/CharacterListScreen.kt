package com.example.marvelchallenge.presentation.screens.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.marvelchallenge.presentation.components.SearchAppBar
import com.example.marvelchallenge.presentation.screens.list.CharacterListState.Error
import com.example.marvelchallenge.presentation.screens.list.CharacterListState.Success
import com.example.marvelchallenge.presentation.model.CharacterUIModel

@Composable
internal fun CharacterListScreen(
  characterListState: CharacterListState,
  searchQuery: String,
  isLoading: Boolean,
  onTypedText: (String) -> Unit,
  onLoadMore: () -> Unit,
  onCharacterClick: (CharacterUIModel) -> Unit
) {
  CharactersList(
    characterListState,
    searchQuery,
    isLoading, onTypedText, onLoadMore, onCharacterClick
  )

}

@Composable private fun ErrorList(errorMessage: String) {
  Column(verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
    Text(text = errorMessage, modifier = Modifier.align(Alignment.CenterHorizontally))
  }
}

@Composable
private fun CharactersList(
  characterListState: CharacterListState,
  searchQuery: String,
  isLoading: Boolean,
  typedText: (String) -> Unit,
  onLoadMore: () -> Unit,
  onCharacterClick: (CharacterUIModel) -> Unit
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
  ) {
    SearchAppBar(
      text = searchQuery,
      onTextChange = {
        typedText(it)
      },
      onSearchClicked = {
        typedText("")
      },
    )
    Spacer(modifier = Modifier.height(16.dp))
    when (characterListState) {
      is Success -> {
        if (characterListState.characters.isEmpty()) {
          EmptyList()
        } else {
          CharacterList(characterListState.characters, isLoading, onLoadMore, onCharacterClick)
        }
      }
      is Error -> {
        ErrorList(characterListState.message)
      }
    }
  }
}

private const val BUFFER = 2

@Composable
private fun CharacterList(
  characters: List<CharacterUIModel>,
  isLoadingNextPage: Boolean,
  onLoadMore: () -> Unit,
  onCharacterClick: (CharacterUIModel) -> Unit
) {
  val listState = rememberLazyListState()

  val reachedBottom: Boolean by remember {
    derivedStateOf {
      val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
      lastVisibleItem?.index != 0 && lastVisibleItem?.index == listState.layoutInfo.totalItemsCount - BUFFER
    }
  }
  LaunchedEffect(reachedBottom) {
    if (reachedBottom) {
      onLoadMore()
    }
  }
  LazyColumn(
    state = listState,
    modifier = Modifier.background(MaterialTheme.colorScheme.background),
    verticalArrangement = Arrangement.spacedBy(16.dp),
  ) {
    items(characters) { character ->
      CharacterItem(
        imageHeight = 240.dp,
        character = character,
        onCharacterClick = onCharacterClick
      )
    }
    item {
      if (isLoadingNextPage) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
          horizontalArrangement = Arrangement.Center,
        ) {
          CircularProgressIndicator()
        }
      }
    }
  }
}

@Composable
private fun EmptyList() {
  Text(text = "There are no characters matching your search")
}

@Composable
private fun CharacterItem(
  imageHeight: Dp,
  character: CharacterUIModel,
  onCharacterClick: (CharacterUIModel) -> Unit
) {

  Column(
    modifier = Modifier
      .fillMaxWidth()
      .clickable { onCharacterClick(character) }
      .padding(8.dp),
  ) {
    SubcomposeAsyncImage(
      model = ImageRequest.Builder(
        LocalContext.current
      )
        .data(character.thumbnailUrl)
        .crossfade(true)
        .build(),
      loading = {
        CircularProgressIndicator()
      },
      contentDescription = "Character ${character.name} Thumbnail",
      alignment = Alignment.Center,
      contentScale = ContentScale.Crop,
      modifier = Modifier
        .height(imageHeight)
        .fillMaxWidth()
        .clip(MaterialTheme.shapes.small),
    )

    Spacer(modifier = Modifier.height(8.dp))

    Text(
      text = character.name,
      style = MaterialTheme.typography.headlineSmall,
      modifier = Modifier.align(Alignment.CenterHorizontally),
    )
  }
}
