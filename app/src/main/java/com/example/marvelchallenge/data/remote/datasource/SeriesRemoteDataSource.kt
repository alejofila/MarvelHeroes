package com.example.marvelchallenge.data.remote.datasource

import com.example.marvelchallenge.core.Either
import com.example.marvelchallenge.data.DataError
import com.example.marvelchallenge.data.remote.MarvelApi
import com.example.marvelchallenge.data.remote.model.SeriesResult
import javax.inject.Inject

class SeriesRemoteDataSource @Inject constructor(
  private val helper: DataFetcherHelper,
  private val api: MarvelApi
) {

  suspend fun getSeriesForCharacter(id: Int): Either<DataError, List<SeriesResult>> {
    return helper.fetchData {
      api.getSeriesForCharacter(id).data.results
    }
  }
}