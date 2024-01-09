package com.example.marvelchallenge.data.remote.datasource

import com.example.marvelchallenge.core.Either
import com.example.marvelchallenge.core.toLeft
import com.example.marvelchallenge.core.toRight
import com.example.marvelchallenge.data.ConnectionHelper
import com.example.marvelchallenge.data.DataError
import com.example.marvelchallenge.data.DataError.NoInternetConnectionError
import javax.inject.Inject

class DataFetcherHelper @Inject constructor(
  private val connectionHelper: ConnectionHelper,
) {

  suspend fun <T> fetchData(
    apiCall: suspend () -> T
  ): Either<DataError, T> {
    return if (connectionHelper.isNetworkAvailable()) {

      runCatching { apiCall() }
        .fold(
          onSuccess = { it.toRight() },
          onFailure = { mapToDataError(it).toLeft() }
        )
    } else {
      NoInternetConnectionError.toLeft()
    }
  }

  private fun mapToDataError(error: Throwable) = DataError.fromException(error)
}