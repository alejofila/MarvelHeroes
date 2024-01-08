package com.example.marvelchallenge.data.remote.datasource

import com.example.marvelchallenge.core.Either
import com.example.marvelchallenge.data.DataError
import com.example.marvelchallenge.data.remote.MarvelApi
import com.example.marvelchallenge.data.remote.model.EventResult
import javax.inject.Inject


class EventsRemoteDataSource @Inject constructor(
  private val helper: DataFetcherHelper,
  private val api: MarvelApi
) {

  suspend fun getEventsForCharacter(id: Int): Either<DataError, List<EventResult>> {
    return helper.fetchData {
      api.getEventsForCharacter(id).data.results
    }
  }
}