package com.example.marvelchallenge.data.mapper

interface BaseDetailsDTOMapper<T, D> {
  fun mapToDomain(remoteItem: T): D
}