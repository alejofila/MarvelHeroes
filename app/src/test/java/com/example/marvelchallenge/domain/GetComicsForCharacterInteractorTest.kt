package com.example.marvelchallenge.domain

import com.example.marvelchallenge.core.toLeft
import com.example.marvelchallenge.core.toRight
import com.example.marvelchallenge.domain.model.Character
import com.example.marvelchallenge.domain.model.Comic
import com.example.marvelchallenge.domain.model.ComicDetails
import com.example.marvelchallenge.domain.repository.ComicRepository
import com.example.marvelchallenge.domain.repository.DomainError
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class GetComicsForCharacterInteractorTest {
  private val comicRepository: ComicRepository = mock()
  private lateinit var getComicsForCharacterInteractor: GetComicsForCharacterInteractor
  private val character =
    Character(
      1,
      "Test",
      "Test",
      "Test",
      emptyList(),
      emptyList(),
      emptyList()
    )

  @Before
  fun setup() {
    getComicsForCharacterInteractor = GetComicsForCharacterInteractor(
      comicRepository
    )
  }

  @Test
  fun `getComicDetails returns character with correct comics`() = runTest {
    val comics = listOf(
      Comic(
        1, "Test", ComicDetails("Test", "Test", 10)
      )
    )
    whenever(comicRepository.getDataForCharacter(character.id)).thenReturn(comics.toRight())

    val result = getComicsForCharacterInteractor(character.id)

    assertThat(comics.toRight(), `is`(result))

  }

  @Test
  fun `getComicDetails returns error when repository fails`() = runTest {
    whenever(comicRepository.getDataForCharacter(character.id)).thenReturn(DomainError.ServerError.toLeft())

    val result = getComicsForCharacterInteractor(character.id)

    assertThat(DomainError.ServerError.toLeft(), `is`(result))
  }
}