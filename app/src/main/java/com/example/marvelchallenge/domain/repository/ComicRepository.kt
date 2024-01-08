package com.example.marvelchallenge.domain.repository

import com.example.marvelchallenge.core.Either
import com.example.marvelchallenge.domain.model.Character
import com.example.marvelchallenge.domain.model.Comic
import com.example.marvelchallenge.domain.model.ComicDetails

interface ComicRepository {

  suspend fun getComicForCharacter(character: Character): Either<DomainError, List<Comic>>

  suspend fun saveComics(comics: List<Comic>)

  suspend fun saveComicDetails(id: Int, comic: ComicDetails)
}