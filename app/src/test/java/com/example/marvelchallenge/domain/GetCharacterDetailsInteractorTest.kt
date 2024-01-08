package com.example.marvelchallenge.domain

import com.example.marvelchallenge.core.toLeft
import com.example.marvelchallenge.core.toRight
import com.example.marvelchallenge.domain.model.Character
import com.example.marvelchallenge.domain.model.Comic
import com.example.marvelchallenge.domain.model.ComicDetails
import com.example.marvelchallenge.domain.model.Event
import com.example.marvelchallenge.domain.model.EventDetails
import com.example.marvelchallenge.domain.model.Series
import com.example.marvelchallenge.domain.model.SeriesDetails
import com.example.marvelchallenge.domain.repository.CharactersRepository
import com.example.marvelchallenge.domain.repository.ComicRepository
import com.example.marvelchallenge.domain.repository.DomainError
import com.example.marvelchallenge.domain.repository.EventsRepository
import com.example.marvelchallenge.domain.repository.SeriesRepository
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class GetCharacterDetailsInteractorTest {

  private val charactersRepository: CharactersRepository = mock()
  private val comicRepository: ComicRepository = mock()
  private val seriesRepository: SeriesRepository = mock()
  private val eventsRepository: EventsRepository = mock()

  private lateinit var getCharacterDetailsInteractor: GetCharacterDetailsInteractor
  private val character =
    Character(1, "Test", "Test", "Test", emptyList(), emptyList(), emptyList())

  private val character2 =
    Character(1, "Test", "Test", "Test", emptyList(), emptyList(), emptyList())
  private val characterList = listOf<Character>(character, character2)

  @Before
  fun setup() {
    getCharacterDetailsInteractor = GetCharacterDetailsInteractor(
      charactersRepository,
      comicRepository,
      seriesRepository,
      eventsRepository
    )
  }

  @Test
  fun `getCharacterInfo returns correct character information`() = runTest {
    whenever(charactersRepository.getCharacterById(1)).thenReturn(character.toRight())

    val result = getCharacterDetailsInteractor.getCharacterInfo(1)

    assertThat(character.toRight(), `is`(result))
  }

  @Test
  fun `getComicDetails returns character with correct comics`() = runTest {
    val comics = listOf(
      Comic(
        1, "Test", ComicDetails("Test", "Test", 10)
      )
    )
    whenever(charactersRepository.getCharacterById(1)).thenReturn(character.toRight())
    whenever(comicRepository.getComicForCharacter(character)).thenReturn(comics.toRight())

    val result = getCharacterDetailsInteractor.getComicDetails(1)

    assertThat(character.copy(comics = comics).toRight(), `is`(result))

  }

  @Test
  fun `getSeriesDetail returns character with correct series`() = runTest {

    val listOfSeries =
      listOf(Series(1, "Test", SeriesDetails("Test", "Test", "Test", "Test", "Test")))
    whenever(charactersRepository.getCharacterById(1)).thenReturn(character.toRight())
    whenever(seriesRepository.getSeriesForCharacter(character)).thenReturn(listOfSeries.toRight())

    val result = getCharacterDetailsInteractor.getSeriesDetail(1)

    assertThat(character.copy(series = listOfSeries).toRight(), `is`(result))
  }

  @Test
  fun `getEventDetails returns character with correct events`() = runTest {
    val events = listOf(
      Event(1, "Test", EventDetails("Test", "Test"))
    )

    whenever(charactersRepository.getCharacterById(1)).thenReturn(character.toRight())
    whenever(eventsRepository.getEventsForCharacter(character)).thenReturn(events.toRight())

    val result = getCharacterDetailsInteractor.getEventDetails(1)

    assertThat(character.copy(events = events).toRight(), `is`(result))
  }

  @Test
  fun `getCharacterInfo returns error when repository fails`() = runTest {
    whenever(charactersRepository.getCharacterById(1)).thenReturn(
      DomainError.ServerError.toLeft()
    )

    val result = getCharacterDetailsInteractor.getCharacterInfo(1)

    assertThat(DomainError.ServerError.toLeft(), `is`(result))
  }

  @Test
  fun `getComicDetails returns error when repository fails`() = runTest {
    whenever(charactersRepository.getCharacterById(1)).thenReturn(DomainError.ServerError.toLeft())

    val result = getCharacterDetailsInteractor.getComicDetails(1)

    assertThat(DomainError.ServerError.toLeft(), `is`(result))
  }

  @Test
  fun `getSeriesDetail returns error when repository fails`() = runTest {
    whenever(charactersRepository.getCharacterById(1)).thenReturn(DomainError.ServerError.toLeft())

    val result = getCharacterDetailsInteractor.getSeriesDetail(1)

    assertThat(DomainError.ServerError.toLeft(), `is`(result))
  }

  @Test
  fun `getEventDetails returns error when repository fails`() = runTest {
    whenever(charactersRepository.getCharacterById(1)).thenReturn(DomainError.ServerError.toLeft())

    val result = getCharacterDetailsInteractor.getEventDetails(1)

    assertThat(DomainError.ServerError.toLeft(), `is`(result))
  }
}
