package com.example.marvelchallenge.domain.repository

sealed interface DomainError {
  data class UnknownError(val message: String) : DomainError
  object ServerError : DomainError
  object NetworkNotAvailable : DomainError
}