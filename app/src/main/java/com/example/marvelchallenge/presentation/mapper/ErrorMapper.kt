package com.example.marvelchallenge.presentation.mapper

import com.example.marvelchallenge.domain.repository.DomainError
import com.example.marvelchallenge.domain.repository.DomainError.NetworkNotAvailable
import com.example.marvelchallenge.domain.repository.DomainError.ServerError
import com.example.marvelchallenge.domain.repository.DomainError.UnknownError
import com.example.marvelchallenge.presentation.screens.list.CharacterListState
import javax.inject.Inject

class ErrorMapper @Inject constructor(){

  fun mapToUIError(domainError: DomainError): CharacterListState.Error {
    return when (domainError) {
      is ServerError -> CharacterListState.Error("There was an error trying to fetch characters, try again later")
      NetworkNotAvailable -> CharacterListState.Error("There is no internet connection available, try to turn it on")
      is UnknownError -> CharacterListState.Error("Unknow error : " + domainError.message)
    }
  }
}