package com.example.marvelchallenge.data.mapper

import com.example.marvelchallenge.data.remote.model.ComicDataItem
import com.example.marvelchallenge.data.remote.model.MarvelComicItem
import com.example.marvelchallenge.domain.model.Comic
import com.example.marvelchallenge.domain.model.ComicDetails
import javax.inject.Inject

class ComicDTOMapper @Inject constructor() {

  fun mapToDomain(marvelComicItem: MarvelComicItem): Comic {
    val id = marvelComicItem.resourceURI.split("/").last().toInt()
    return Comic(
      id = id,
      name = marvelComicItem.name,
    )
  }

  fun mapDetailsToDomain(comicDataItem: ComicDataItem): Comic {
    return with(comicDataItem) {
      Comic(
        id = id,
        name = title,
        details = ComicDetails(
          description = description,
          thumbnailUrl = thumbnail.path + "." + thumbnail.extension,
          pageCount = pageCount,
        )
      )
    }

  }

  fun mapToDomain(comicResponse: ComicDataItem): ComicDetails {
    return with(comicResponse) {
      ComicDetails(
        description = description,
        thumbnailUrl = thumbnail.path + "." + thumbnail.extension,
        pageCount = pageCount,
      )
    }

  }
}