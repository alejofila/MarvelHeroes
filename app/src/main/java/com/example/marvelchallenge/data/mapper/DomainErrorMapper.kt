package com.example.marvelchallenge.data.mapper

import com.example.marvelchallenge.data.DataError
import com.example.marvelchallenge.data.DataError.FailedStatusError
import com.example.marvelchallenge.data.DataError.JSONParsingError
import com.example.marvelchallenge.data.DataError.NetworkError
import com.example.marvelchallenge.data.DataError.NoInternetConnectionError
import com.example.marvelchallenge.data.DataError.UnknownError
import com.example.marvelchallenge.domain.repository.DomainError
import com.example.marvelchallenge.domain.repository.DomainError.NetworkNotAvailable
import com.example.marvelchallenge.domain.repository.DomainError.ServerError
import javax.inject.Inject

class DomainErrorMapper @Inject constructor() {

  fun map(error: DataError): DomainError {
    return when (error) {
      NoInternetConnectionError, is NetworkError -> NetworkNotAvailable
      FailedStatusError, JSONParsingError -> ServerError
      is UnknownError -> DomainError.UnknownError(error.message)
    }
  }
}
