package com.example.marvelchallenge.presentation.mapper

import com.example.marvelchallenge.domain.model.Event
import com.example.marvelchallenge.presentation.model.EventUIModel
import javax.inject.Inject

class EventMapperUIModel @Inject constructor() {
  fun mapToUIModel(event: Event): EventUIModel {
    return with(event) {
      EventUIModel(
        id = id,
        title = name,
        description = details?.description ?: "No description found",
        thumbnailUrl = details?.thumbnailUrl ?: "No thumbnail found"
      )
    }
  }
}