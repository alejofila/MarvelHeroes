package com.example.marvelchallenge.data.mapper

import com.example.marvelchallenge.data.remote.model.EventResult
import com.example.marvelchallenge.data.remote.model.MarvelEventItem
import com.example.marvelchallenge.domain.model.Event
import com.example.marvelchallenge.domain.model.EventDetails
import javax.inject.Inject

class EventDetailsDTOMapper @Inject constructor() {
  fun mapToDomain(eventResponse: EventResult): EventDetails {
    return with(eventResponse) {
      EventDetails(
        description = description,
        thumbnailUrl = thumbnail.path + "." + thumbnail.extension,
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