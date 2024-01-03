package com.example.marvelchallenge.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.marvelchallenge.ui.theme.MarvelChallengeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MarvelChallengeTheme {
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

data class Character(val id: Int, val name: String, val thumbnailUrl: String)

val stockData = listOf(
    Character(1, "Iron Man", "https://i.annihil.us/u/prod/marvel/i/mg/c/60/55b6a28ef24fa.jpg"),
    Character(2, "Thor", "https://i.annihil.us/u/prod/marvel/i/mg/d/d0/5269657a74350.jpg"),
    Character(3, "Black Widow", "https://i.annihil.us/u/prod/marvel/i/mg/f/30/50fecad1f395b.jpg"),
    Character(4, "Captain America", "https://i.annihil.us/u/prod/marvel/i/mg/9/80/537ba5b368b7d.jpg"),
    Character(5, "Hulk", "https://i.annihil.us/u/prod/marvel/i/mg/5/a0/538615ca33ab0.jpg"),
    Character(6, "Spider-Man", "https://i.annihil.us/u/prod/marvel/i/mg/3/50/526548a343e4b.jpg"),
    Character(7, "Doctor Strange", "https://i.annihil.us/u/prod/marvel/i/mg/5/f0/5261a85a501fe.jpg"),
    Character(8, "Black Panther", "https://i.annihil.us/u/prod/marvel/i/mg/6/60/5261a80a67e7d.jpg"),
    Character(9, "Scarlet Witch", "https://i.annihil.us/u/prod/marvel/i/mg/6/70/5261a6748f4d4.jpg"),
    Character(10, "Wolverine", "https://i.annihil.us/u/prod/marvel/i/mg/9/00/537bcb1133fd7.jpg"),
)

@Composable
fun HomeView() {
    var searchQuery by remember { mutableStateOf("") }
    var isSearchFocused by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        SearchBar(
            searchQuery = searchQuery,
            onSearchQueryChanged = {
                searchQuery = it
            },
            onFocusChanged = {
                isSearchFocused = it
            },
        )
        Spacer(modifier = Modifier.height(16.dp))
        CharacterList(searchQuery)
    }
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    onFocusChanged: (Boolean) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        value = searchQuery,
        onValueChange = onSearchQueryChanged,
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                onFocusChanged(focusState.isFocused)
            },
        label = { Text("Search") },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
        },
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Sentences,
            imeAction = ImeAction.Done,
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
            },
        ),
    )
}

@Composable
fun CharacterList(query: String = "") {
    val filteredCharacters = stockData.filter {
        it.name.contains(query, ignoreCase = true)
    }
    if (filteredCharacters.isEmpty()) {
        Text(text = "There is no here matching '$query' text")
    } else {
        LazyColumn {
            items(filteredCharacters) { character ->
                CharacterItem(character = character)
            }
        }
    }
}

@Composable
fun CharacterItem(character: Character) {
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

@Preview(showBackground = true)
@Composable
fun HomeViewPreview() {
    HomeView()
}
