package com.example.marvelchallenge.presentation.mapper

import com.example.marvelchallenge.domain.model.Comic
import com.example.marvelchallenge.presentation.model.ComicUIModel
import javax.inject.Inject

class ComicMapperUIModel @Inject constructor() {
  fun mapToUIModel(comic: Comic): ComicUIModel {
    return with(comic) {
      ComicUIModel(
        id = id,
        name = name,
        description = details?.description ?: "No description found",
        thumbnailUrl = details?.thumbnailUrl ?: "No thumbnail found"
      )
    }
  }
}