package com.example.marvelchallenge.domain

import com.example.marvelchallenge.core.toLeft
import com.example.marvelchallenge.core.toRight
import com.example.marvelchallenge.domain.model.Character
import com.example.marvelchallenge.domain.model.Series
import com.example.marvelchallenge.domain.model.SeriesDetails
import com.example.marvelchallenge.domain.repository.DomainError
import com.example.marvelchallenge.domain.repository.SeriesRepository
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class GetSeriesForCharacterInteractorTest {
  private val seriesRepository: SeriesRepository = mock()
  private lateinit var getSeriesForCharacterInteractor: GetSeriesForCharacterInteractor
  private val character =
    Character(1, "Test", "Test", "Test", emptyList(), emptyList(), emptyList())

  @Before
  fun setup() {
    getSeriesForCharacterInteractor = GetSeriesForCharacterInteractor(
      seriesRepository
    )
  }

  @Test
  fun `getSeriesDetails returns character with correct series`() = runTest {
    val series = listOf(
      Series(
        1, "Test", SeriesDetails("Test", "Test", "Test", "Test", "Test")
      )
    )
    whenever(seriesRepository.getDataForCharacter(character.id)).thenReturn(series.toRight())

    val result = getSeriesForCharacterInteractor(character.id)

    assertThat(series.toRight(), `is`(result))
  }

  @Test
  fun `getSeriesDetails returns error when repository fails`() = runTest {
    whenever(seriesRepository.getDataForCharacter(character.id)).thenReturn(DomainError.ServerError.toLeft())

    val result = getSeriesForCharacterInteractor(character.id)

    assertThat(DomainError.ServerError.toLeft(), `is`(result))
  }
}