package com.example.marvelchallenge.data

import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonEncodingException
import retrofit2.HttpException
import java.io.IOException

sealed class DataError {
  object NoInternetConnectionError : DataError()
  object FailedStatusError : DataError()
  object JSONParsingError : DataError()
  object NetworkError : DataError()
  data class UnknownError(val message: String) : DataError()

  companion object {
    fun fromException(error: Throwable): DataError {
      return when (error) {
        is HttpException -> FailedStatusError
        is JsonDataException, is JsonEncodingException -> JSONParsingError
        is IOException -> NetworkError
        else -> UnknownError(error.message ?: "Unknown error")
      }
    }
  }
}