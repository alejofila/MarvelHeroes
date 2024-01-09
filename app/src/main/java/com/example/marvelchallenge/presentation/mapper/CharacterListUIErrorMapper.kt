package com.example.marvelchallenge.presentation.mapper

import com.example.marvelchallenge.domain.repository.DomainError
import com.example.marvelchallenge.domain.repository.DomainError.MissingAPIKey
import com.example.marvelchallenge.domain.repository.DomainError.NetworkNotAvailable
import com.example.marvelchallenge.domain.repository.DomainError.RateExceeded
import com.example.marvelchallenge.domain.repository.DomainError.ServerError
import com.example.marvelchallenge.domain.repository.DomainError.UnknownError
import com.example.marvelchallenge.presentation.screens.list.CharacterListState
import com.example.marvelchallenge.presentation.screens.list.CharacterListState.Error
import javax.inject.Inject

internal class CharacterListUIErrorMapper @Inject constructor() {

  fun mapToUIError(domainError: DomainError): Error {
    return when (domainError) {
      is ServerError -> Error("There was an error trying to fetch characters, try again later")
      NetworkNotAvailable -> Error("There is no internet connection available, try to turn it on")
      is UnknownError -> Error("Unknow error : " + domainError.message)
      MissingAPIKey -> (Error("Missing API Key, check the readme to see where to put your marvel api key"))
      RateExceeded -> Error("You have exceeded the rate limit, try again later or change the API key")
    }
  }
}