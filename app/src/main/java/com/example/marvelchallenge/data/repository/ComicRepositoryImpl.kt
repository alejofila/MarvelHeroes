package com.example.marvelchallenge.data.repository

import com.example.marvelchallenge.core.Either
import com.example.marvelchallenge.data.mapper.ComicDTOMapper
import com.example.marvelchallenge.data.mapper.DomainErrorMapper
import com.example.marvelchallenge.data.remote.datasource.ComicsRemoteDataSource
import com.example.marvelchallenge.data.remote.model.ComicResult
import com.example.marvelchallenge.domain.model.Character
import com.example.marvelchallenge.domain.model.Comic
import com.example.marvelchallenge.domain.model.ComicDetails
import com.example.marvelchallenge.domain.repository.ComicRepository
import com.example.marvelchallenge.domain.repository.DomainError
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ComicRepositoryImpl @Inject constructor(
  private val coroutineDispatcher: CoroutineDispatcher,
  private val domainErrorMapper: DomainErrorMapper,
  private val comicsRemoteDataSource: ComicsRemoteDataSource,
  private val mapper: ComicDTOMapper
) : ComicRepository {

  private val inMemoryCache = mutableMapOf<Int, Comic>()
  override suspend fun getComicForCharacter(character: Character): Either<DomainError, List<Comic>> {

    return if (character.comics.any { comic -> comic.details == null }) {
      val result = withContext(coroutineDispatcher) {
        comicsRemoteDataSource.getComicsForCharacter(character.id)
      }
      result.mapLeft { mapError -> domainErrorMapper.map(mapError) }.map { comics ->
        comics.map { remoteComic ->
          mapComic(remoteComic)
        }
      }
    } else {
      Either.Right(character.comics)
    }
  }

  private fun mapComic(remoteComic: ComicResult): Comic {
    val comicDetails = mapper.mapToDomain(remoteComic)
    return Comic(
      id = remoteComic.id,
      name = remoteComic.title,
      details = comicDetails
    )
  }

  override suspend fun saveComics(comics: List<Comic>) {
    comics.forEach { comic ->
      inMemoryCache[comic.id] = comic
    }
  }

  override suspend fun saveComicDetails(id: Int, comic: ComicDetails) {
    inMemoryCache[id]?.let { cachedComic ->
      inMemoryCache[id] = cachedComic.copy(details = comic)
    }
  }
}