package com.example.marvelchallenge.data.remote.datasource

import com.example.marvelchallenge.core.Either
import com.example.marvelchallenge.data.DataError
import com.example.marvelchallenge.data.remote.MarvelApi
import com.example.marvelchallenge.data.remote.model.ComicResult
import javax.inject.Inject

class ComicsRemoteDataSource @Inject constructor(
  private val helper: DataFetcherHelper,
  private val api: MarvelApi
) {

  suspend fun getComicsForCharacter(id: Int): Either<DataError, List<ComicResult>> {
    return helper.fetchData {
      api.getComicsForCharacter(id).data.results
    }
  }
}