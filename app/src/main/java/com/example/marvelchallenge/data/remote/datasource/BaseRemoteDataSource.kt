package com.example.marvelchallenge.data.remote.datasource

import com.example.marvelchallenge.core.Either
import com.example.marvelchallenge.data.DataError

interface BaseRemoteDataSource<T> {
  suspend fun getItemsForCharacter(characterId: Int): Either<DataError, List<T>>
}