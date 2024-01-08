package com.example.marvelchallenge.presentation.screens.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.marvelchallenge.presentation.model.CharacterUIModel
import com.example.marvelchallenge.presentation.model.ComicUIModel
import com.example.marvelchallenge.presentation.model.EventUIModel
import com.example.marvelchallenge.presentation.model.SeriesUIModel
import com.example.marvelchallenge.presentation.screens.details.DetailsState.Error
import com.example.marvelchallenge.presentation.screens.details.DetailsState.Loading
import com.example.marvelchallenge.presentation.screens.details.DetailsState.Success

@Composable
fun CharacterDetailsScreen(
  characterState: DetailsState<CharacterUIModel>,
  comicsState: DetailsState<ComicUIModel>,
  eventsState: DetailsState<EventUIModel>,
  seriesState: DetailsState<SeriesUIModel>
) {
  Column(
    modifier = Modifier
      .verticalScroll(rememberScrollState())
      .fillMaxSize()
      .padding(16.dp)
  ) {
    when (characterState) {
      is Error -> Text(text = characterState.message)

      Loading -> Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(8.dp),
        horizontalArrangement = Arrangement.Center,
      ) {
        CircularProgressIndicator()
      }

      is Success -> {
        val character = characterState.data.first()
        SubcomposeAsyncImage(
          model = ImageRequest.Builder(LocalContext.current)
            .data(character.thumbnailUrl)
            .crossfade(true)
            .build(),
          contentDescription = "Character $characterState Thumbnail",
          contentScale = ContentScale.Crop,
          loading = {
            CircularProgressIndicator()
          },
          modifier = Modifier
            .height(240.dp)
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.small)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
          text = character.name,
          style = MaterialTheme.typography.headlineSmall,
          modifier = Modifier.align(CenterHorizontally)
        )
      }
    }


    Spacer(modifier = Modifier.height(16.dp))
    ComicsSection(comicsState)
    SeriesSection(seriesState)
    EventsSection(eventsState)
  }
}

@Composable
private fun ComicsSection(
  comics: DetailsState<ComicUIModel>,
) {
  Text(text = "Comics", style = MaterialTheme.typography.headlineSmall)
  when (comics) {
    is Success -> {
      HorizontalScrollView(comics.data, Modifier.padding(top = 16.dp)) {
        ItemCard(it.name, it.thumbnailUrl)
      }
    }

    is Error -> {
      Text(text = comics.message)
    }

    is Loading -> {
      CircularProgressIndicator(modifier = Modifier.padding(16.dp))
    }
  }
}

@Composable
private fun SeriesSection(
  seriesState: DetailsState<SeriesUIModel>,
) {
  Text(text = "Series", style = MaterialTheme.typography.headlineSmall)
  when (seriesState) {
    is Success -> {

      HorizontalScrollView(seriesState.data, Modifier.padding(top = 16.dp)) {
        ItemCard(it.title, it.thumbnailUrl)
      }
    }

    is Error -> {
      Text(text = seriesState.message)
    }

    is Loading -> {
      CircularProgressIndicator(modifier = Modifier.padding(16.dp))
    }
  }
}

@Composable
private fun EventsSection(
  eventsSection: DetailsState<EventUIModel>,
) {
  Text(text = "Events", style = MaterialTheme.typography.headlineSmall)
  when (eventsSection) {
    is Success -> {

      HorizontalScrollView(eventsSection.data, Modifier.padding(top = 16.dp)) {
        ItemCard(it.title, it.thumbnailUrl)
      }
    }

    is Error -> {
      Text(text = eventsSection.message)
    }

    is Loading -> {
      CircularProgressIndicator(modifier = Modifier.padding(16.dp))
    }
  }
}

@Composable
fun <T> HorizontalScrollView(
  items: List<T>,
  modifier: Modifier = Modifier,
  itemContent: @Composable (T) -> Unit
) {
  Column(modifier = modifier) {
    Spacer(modifier = Modifier.height(8.dp))
    if (items.isEmpty()) {
      Text(
        text = "There are no items in this section",
        modifier = Modifier
          .fillMaxWidth()
          .align(CenterHorizontally)
      )
    }
    LazyRow {
      items(items) { item ->
        itemContent(item)
      }
    }
  }
}

@Composable
fun ItemCard(name: String, thumbnailUrl: String) {
  Card(
    modifier = Modifier
      .padding(end = 8.dp)
      .width(120.dp)
      .height(180.dp)
  ) {
    Column {
      SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
          .data(thumbnailUrl)
          .crossfade(true)
          .build(),
        loading = {
          CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        },
        contentDescription = "$name Thumbnail",
        contentScale = ContentScale.Crop,
        modifier = Modifier
          .height(120.dp)
          .fillMaxWidth()
      )
      Text(
        text = name,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(8.dp)
      )
      Spacer(modifier = Modifier.height(8.dp))
    }
  }
}

