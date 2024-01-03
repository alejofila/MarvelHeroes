package com.example.marvelchallenge.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.marvelchallenge.presentation.screens.CharacterListScreen
import com.example.marvelchallenge.presentation.viewmodel.CharactersListViewModel
import com.example.marvelchallenge.ui.theme.MarvelChallengeTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      MarvelChallengeTheme(dynamicColor = false) {
        // A surface container using the 'background' color from the theme
        Surface(
          modifier = Modifier.fillMaxSize(),
          color = MaterialTheme.colorScheme.background,
        ) {
          HomeView()
        }
      }
    }
  }
}

@Composable
fun HomeView() {

  val viewModel: CharactersListViewModel = viewModel()
  val charactersState by viewModel.uiState.collectAsState()
  CharacterListScreen(charactersState) { typedText ->
    viewModel.queryCharacters(typedText)
  }
}

@Preview(showBackground = true)
@Composable
fun HomeViewPreview() {
  HomeView()
}
