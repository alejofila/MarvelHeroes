package com.example.marvelchallenge.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EventsResponse(
  @Json(name = "code") val code: Int,
  @Json(name = "status") val status: String,
  @Json(name = "data") val data: EventData
)

@JsonClass(generateAdapter = true)
data class EventData(
  @Json(name = "offset") val offset: Int,
  @Json(name = "limit") val limit: Int,
  @Json(name = "total") val total: Int,
  @Json(name = "count") val count: Int,
  @Json(name = "results") val results: List<EventResult>
)

@JsonClass(generateAdapter = true)
data class EventResult(
  @Json(name = "id") val id: Int,
  @Json(name = "title") val title: String,
  @Json(name = "description") val description: String,
  @Json(name = "thumbnail") val thumbnail: MarvelThumbnail,
)