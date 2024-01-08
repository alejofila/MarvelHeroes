package com.example.marvelchallenge.data.mapper

import com.example.marvelchallenge.data.remote.model.ComicResult
import com.example.marvelchallenge.data.remote.model.MarvelComicItem
import com.example.marvelchallenge.domain.model.Comic
import com.example.marvelchallenge.domain.model.ComicDetails
import javax.inject.Inject

class ComicDTOMapper @Inject constructor() {

  fun mapToDomain(marvelComicItem: MarvelComicItem): Comic {
    val id = marvelComicItem.resourceURI.split("/").last().toInt()
    return Comic(
      id = id,
      name = marvelComicItem.name
    )
  }

  fun mapToDomain(comicResponse: ComicResult): ComicDetails {
    return with(comicResponse) {
      ComicDetails(
        description = description,
        thumbnailUrl = thumbnail.path + "." + thumbnail.extension,
        pageCount = pageCount,
      )
    }

  }
}