package com.example.marvelchallenge.data.mapper

import com.example.marvelchallenge.data.remote.model.MarvelSeriesItem
import com.example.marvelchallenge.data.remote.model.SeriesResult
import com.example.marvelchallenge.domain.model.Series
import com.example.marvelchallenge.domain.model.SeriesDetails
import javax.inject.Inject

class SeriesDetailsDTOMapper @Inject constructor() {
  fun mapToDomain(seriesResponse: SeriesResult): SeriesDetails {
    return with(seriesResponse) {
      SeriesDetails(
        description = "No description available",
        thumbnailUrl = thumbnail.path + "." + thumbnail.extension,
        startYear = startYear.toString(),
        endYear = endYear.toString(),
        rating = rating
      )
    }
  }

  fun mapToDomain(marvelSeriesItem: MarvelSeriesItem): Series {
    val id = marvelSeriesItem.resourceURI.split("/").last().toInt()
    return Series(
      id = id,
      name = marvelSeriesItem.name
    )
  }
}