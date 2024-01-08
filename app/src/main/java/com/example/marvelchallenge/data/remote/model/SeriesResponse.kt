package com.example.marvelchallenge.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SeriesResponse(
  @Json(name = "code") val code: Int,
  @Json(name = "status") val status: String,
  @Json(name = "data") val data: SeriesData
)

@JsonClass(generateAdapter = true)
data class SeriesData(
  @Json(name = "offset") val offset: Int,
  @Json(name = "limit") val limit: Int,
  @Json(name = "total") val total: Int,
  @Json(name = "count") val count: Int,
  @Json(name = "results") val results: List<SeriesResult>
)

@JsonClass(generateAdapter = true)
data class SeriesResult(
  @Json(name = "id") val id: Int,
  @Json(name = "title") val title: String,
  @Json(name = "description") val description: String?,
  @Json(name = "urls") val urls: List<MarvelUrl>,
  @Json(name = "startYear") val startYear: Long,
  @Json(name = "endYear") val endYear: Long,
  @Json(name = "rating") val rating: String,
  @Json(name = "thumbnail") val thumbnail: MarvelThumbnail,

  )