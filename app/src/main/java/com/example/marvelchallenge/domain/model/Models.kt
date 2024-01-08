package com.example.marvelchallenge.domain.model

data class Character(
  val id: Int,
  val name: String,
  val description: String,
  val thumbnailUrl: String,
  val comics: List<Comic>,
  val series: List<Series>,
  val events: List<Event>
)

data class Comic(
  val id: Int,
  val name: String,
  val details: ComicDetails? = null
)

data class ComicDetails(
  val description: String,
  val thumbnailUrl: String,
  val pageCount: Int,
)

data class Series(
  val id: Int,
  val name: String,
  val details: SeriesDetails? = null
)

data class SeriesDetails(
  val description: String,
  val thumbnailUrl: String,
  val startYear: String,
  val endYear: String,
  val rating: String
)

data class Event(
  val id: Int,
  val name: String,
  val details: EventDetails? = null
)

data class EventDetails(
  val description: String,
  val thumbnailUrl: String,
)