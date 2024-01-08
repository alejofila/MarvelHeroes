package com.example.marvelchallenge.data.mapper

import com.example.marvelchallenge.data.remote.model.EventDataItem
import com.example.marvelchallenge.data.remote.model.MarvelEventItem
import com.example.marvelchallenge.domain.model.Event
import com.example.marvelchallenge.domain.model.EventDetails
import javax.inject.Inject

class EventDTOMapper @Inject constructor() {

  fun mapDetailsToDomain(comicResult: EventDataItem): Event {
    return with(comicResult) {
      Event(
        id = id,
        name = title,
        details = EventDetails(
          description = description,
          thumbnailUrl = thumbnail.path + "." + thumbnail.extension,
        )
      )
    }

  }

  fun mapToDomain(marvelEventItem: MarvelEventItem): Event {
    val id = marvelEventItem.resourceURI.split("/").last().toInt()
    return Event(
      id = id,
      name = marvelEventItem.name
    )
  }
}