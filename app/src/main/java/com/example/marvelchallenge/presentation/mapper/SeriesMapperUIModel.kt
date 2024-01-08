package com.example.marvelchallenge.presentation.mapper

import com.example.marvelchallenge.domain.model.Series
import com.example.marvelchallenge.presentation.model.SeriesUIModel
import javax.inject.Inject

class SeriesMapperUIModel @Inject constructor() {
  fun mapToUIModel(series: Series): SeriesUIModel {
    return with(series) {
      SeriesUIModel(
        id = id,
        title = name,
        description = details?.description ?: "No description found",
        thumbnailUrl = details?.thumbnailUrl ?: "No thumbnail found"
      )
    }
  }
}