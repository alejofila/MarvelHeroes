package com.example.marvelchallenge.domain.repository

sealed interface DomainError {
  object MissingAPIKey : DomainError
  object RateExceeded : DomainError
  data class UnknownError(val message: String) : DomainError
  object ServerError : DomainError
  object NetworkNotAvailable : DomainError
}