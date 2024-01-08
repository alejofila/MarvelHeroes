package com.example.marvelchallenge.data.remote.datasource

import com.example.marvelchallenge.core.Either
import com.example.marvelchallenge.data.DataError
import com.example.marvelchallenge.data.remote.MarvelApi
import com.example.marvelchallenge.data.remote.model.EventDataItem
import javax.inject.Inject

class EventsRemoteDataSource @Inject constructor(
  private val helper: DataFetcherHelper,
  private val api: MarvelApi
) : BaseRemoteDataSource<EventDataItem> {

  override suspend fun getItemsForCharacter(characterId: Int): Either<DataError, List<EventDataItem>> {
    return helper.fetchData {
      api.getEventsForCharacter(characterId).data.results
    }
  }
}