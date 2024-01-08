package com.example.marvelchallenge.data.remote.datasource

import com.example.marvelchallenge.core.Either
import com.example.marvelchallenge.data.DataError
import com.example.marvelchallenge.data.remote.MarvelApi
import com.example.marvelchallenge.data.remote.model.SeriesDataItem
import javax.inject.Inject

class SeriesRemoteDataSource @Inject constructor(
  private val helper: DataFetcherHelper,
  private val api: MarvelApi
) : BaseRemoteDataSource<SeriesDataItem> {

  override suspend fun getItemsForCharacter(characterId: Int): Either<DataError, List<SeriesDataItem>> {
    return helper.fetchData {
      api.getSeriesForCharacter(characterId).data.results
    }
  }
}