package com.example.marvelchallenge.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CharactersResponse(
  @Json(name = "code") val code: Int,
  @Json(name = "status") val status: String,
  @Json(name = "copyright") val copyright: String,
  @Json(name = "attributionText") val attributionText: String,
  @Json(name = "attributionHTML") val attributionHTML: String,
  @Json(name = "etag") val etag: String,
  @Json(name = "data") val data: MarvelData
)

@JsonClass(generateAdapter = true)
data class MarvelData(
  @Json(name = "offset") val offset: Int,
  @Json(name = "limit") val limit: Int,
  @Json(name = "total") val total: Int,
  @Json(name = "count") val count: Int,
  @Json(name = "results") val results: List<MarvelCharacter>
)

@JsonClass(generateAdapter = true)
data class MarvelCharacter(
  @Json(name = "id") val id: Int,
  @Json(name = "name") val name: String,
  @Json(name = "description") val description: String,
  @Json(name = "modified") val modified: String,
  @Json(name = "thumbnail") val thumbnail: MarvelThumbnail,
  @Json(name = "resourceURI") val resourceURI: String,
  @Json(name = "comics") val comics: MarvelComics,
  @Json(name = "series") val series: MarvelSeries,
  @Json(name = "stories") val stories: MarvelStories,
  @Json(name = "events") val events: MarvelEvents,
  @Json(name = "urls") val urls: List<MarvelUrl>
)

@JsonClass(generateAdapter = true)
data class MarvelThumbnail(
  @Json(name = "path") val path: String,
  @Json(name = "extension") val extension: String
)

@JsonClass(generateAdapter = true)
data class MarvelComics(
  @Json(name = "available") val available: Int,
  @Json(name = "collectionURI") val collectionURI: String,
  @Json(name = "items") val items: List<MarvelComicItem>,
  @Json(name = "returned") val returned: Int
)

@JsonClass(generateAdapter = true)
data class MarvelComicItem(
  @Json(name = "resourceURI") val resourceURI: String,
  @Json(name = "name") val name: String
)

@JsonClass(generateAdapter = true)
data class MarvelSeries(
  @Json(name = "available") val available: Int,
  @Json(name = "collectionURI") val collectionURI: String,
  @Json(name = "items") val items: List<MarvelSeriesItem>,
  @Json(name = "returned") val returned: Int
)

@JsonClass(generateAdapter = true)
data class MarvelSeriesItem(
  @Json(name = "resourceURI") val resourceURI: String,
  @Json(name = "name") val name: String
)

@JsonClass(generateAdapter = true)
data class MarvelStories(
  @Json(name = "available") val available: Int,
  @Json(name = "collectionURI") val collectionURI: String,
  @Json(name = "items") val items: List<MarvelStoryItem>,
  @Json(name = "returned") val returned: Int
)

@JsonClass(generateAdapter = true)
data class MarvelStoryItem(
  @Json(name = "resourceURI") val resourceURI: String,
  @Json(name = "name") val name: String,
  @Json(name = "type") val type: String
)

@JsonClass(generateAdapter = true)
data class MarvelEvents(
  @Json(name = "available") val available: Int,
  @Json(name = "collectionURI") val collectionURI: String,
  @Json(name = "items") val items: List<MarvelEventItem>,
  @Json(name = "returned") val returned: Int
)

@JsonClass(generateAdapter = true)
data class MarvelEventItem(
  @Json(name = "resourceURI") val resourceURI: String,
  @Json(name = "name") val name: String
)

@JsonClass(generateAdapter = true)
data class MarvelUrl(
  @Json(name = "type") val type: String,
  @Json(name = "url") val url: String
)