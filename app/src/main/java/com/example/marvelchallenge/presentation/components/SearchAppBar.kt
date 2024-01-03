package com.example.marvelchallenge.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchAppBar(
  text: String,
  onTextChange: (String) -> Unit,
  onSearchClicked: (String) -> Unit,
) {
  Surface(
    modifier = Modifier
      .fillMaxWidth()
      .height(56.dp),
    shadowElevation = 10.dp,
    color = MaterialTheme.colorScheme.primary,
  ) {
    TextField(
      modifier = Modifier.fillMaxWidth(),
      value = text,

      onValueChange = {
        onTextChange(it)
      },
      placeholder = {
        Text(
          text = "Type here to search",
          modifier = Modifier.alpha(0.4f),
          color = Color.White,

          )
      },
      textStyle = MaterialTheme.typography.titleSmall,
      singleLine = true,
      leadingIcon = {
        IconButton(
          modifier = Modifier.alpha(0.4f),
          onClick = { },
        ) {
          Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "Search Icon",
            tint = Color.White,
          )
        }
      },
      keyboardOptions = KeyboardOptions(
        imeAction = ImeAction.Search,
      ),
      keyboardActions = KeyboardActions(
        onSearch = {
          onSearchClicked(text)
        },
      ),
      colors = TextFieldDefaults.textFieldColors(
        containerColor = Color.Transparent,
        cursorColor = Color.White.copy(alpha = 0.4f),
        textColor = Color.White,
      ),

      )
  }
}

@Composable
@Preview
fun SearchAppBarPreview() {
  SearchAppBar(
    text = "some random text",
    onTextChange = { },
    onSearchClicked = {},
  )
}
