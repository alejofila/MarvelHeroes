package com.example.marvelchallenge.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ComicsResponse(
  @Json(name = "code") val code: Int,
  @Json(name = "status") val status: String,
  @Json(name = "data") val data: ComicData
)

@JsonClass(generateAdapter = true)
data class ComicData(
  @Json(name = "offset") val offset: Int,
  @Json(name = "limit") val limit: Int,
  @Json(name = "total") val total: Int,
  @Json(name = "count") val count: Int,
  @Json(name = "results") val results: List<ComicDataItem>
)

@JsonClass(generateAdapter = true)
data class ComicDataItem(
  @Json(name = "id") val id: Int,
  @Json(name = "title") val title: String,
  @Json(name = "description") val description: String,
  @Json(name = "pageCount") val pageCount: Int,
  @Json(name = "thumbnail") val thumbnail: MarvelThumbnail,
  @Json(name = "images") val images: List<MarvelThumbnail>,
)