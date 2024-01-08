package com.example.marvelchallenge.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.marvelchallenge.navigation.MarvelScreen
import com.example.marvelchallenge.presentation.screens.details.CharacterDetailsScreen
import com.example.marvelchallenge.presentation.screens.details.CharacterDetailsScreenUIState
import com.example.marvelchallenge.presentation.screens.details.CharacterDetailsViewModel
import com.example.marvelchallenge.presentation.screens.list.CharacterListScreen
import com.example.marvelchallenge.presentation.screens.list.CharactersListViewModel
import com.example.marvelchallenge.ui.theme.MarvelChallengeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val listViewModel: CharactersListViewModel by viewModels()
    val detailsViewModel: CharacterDetailsViewModel by viewModels()
    setContent {
      MarvelChallengeTheme(dynamicColor = false) {
        // A surface container using the 'background' color from the theme
        Surface(
          modifier = Modifier.fillMaxSize(),
          color = MaterialTheme.colorScheme.background,
        ) {
          val navController = rememberNavController()
          NavHost(
            navController = navController,
            startDestination = MarvelScreen.CharacterList.name
          ) {
            composable(MarvelScreen.CharacterList.name) {
              val listState by listViewModel.characterScreenUIState.collectAsState()
              CharacterListScreen(
                listState.characterListState,
                listState.searchQuery,
                listState.isLoading,
                onTypedText = { text ->
                  listViewModel.queryCharacters(text)
                },
                onLoadMore = {
                  listViewModel.queryNextPage()
                },
                onCharacterClick = { character ->
                  navController.navigate("${MarvelScreen.CharacterDetails.name}/${character.id}")
                })
            }
            composable("${MarvelScreen.CharacterDetails.name}/{characterId}") { backStackEntry ->
              val characterId = backStackEntry.arguments?.getString("characterId")
              val parsedCharacterId = characterId?.toInt()
              val detailsState by detailsViewModel.characterDetailState.collectAsState()
              parsedCharacterId?.let { characterID ->
                CharacterDetailScreen(detailsState)
                detailsViewModel.fetchCharacterDetails(characterID)
              }
            }
          }
        }
      }
    }
  }

  @Composable
  private fun CharacterDetailScreen(
    detailsState: CharacterDetailsScreenUIState,
  ) {
    CharacterDetailsScreen(
      detailsState.characterState,
      detailsState.comicState,
      detailsState.eventState,
      detailsState.seriesState
    )
  }
}

