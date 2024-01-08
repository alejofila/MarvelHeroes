package com.example.marvelchallenge.data.remote.datasource

import com.example.marvelchallenge.core.Either
import com.example.marvelchallenge.data.DataError
import com.example.marvelchallenge.data.remote.MarvelApi
import com.example.marvelchallenge.data.remote.model.MarvelCharacter
import javax.inject.Inject

class CharactersRemoteDataSource @Inject constructor(
  private val helper: DataFetcherHelper,
  private val api: MarvelApi
) {

  suspend fun getCharacters(query: String?, offset: Int): Either<DataError, List<MarvelCharacter>> {
    return helper.fetchData {
      api.getCharacters(nameStartsWith = query, offset = offset)
        .data
        .results
    }
  }
}