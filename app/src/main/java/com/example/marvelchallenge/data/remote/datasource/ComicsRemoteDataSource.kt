package com.example.marvelchallenge.data.remote.datasource

import com.example.marvelchallenge.core.Either
import com.example.marvelchallenge.data.DataError
import com.example.marvelchallenge.data.remote.MarvelApi
import com.example.marvelchallenge.data.remote.model.ComicDataItem
import javax.inject.Inject

class ComicsRemoteDataSource @Inject constructor(
  private val helper: DataFetcherHelper,
  private val api: MarvelApi
): BaseRemoteDataSource<ComicDataItem>{

  override suspend fun getItemsForCharacter(characterId: Int): Either<DataError, List<ComicDataItem>> {
    return helper.fetchData {
      api.getComicsForCharacter(characterId).data.results
    }
  }
}