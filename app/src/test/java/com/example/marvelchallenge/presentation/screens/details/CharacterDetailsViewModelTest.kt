package com.example.marvelchallenge.presentation.screens.details

import com.example.marvelchallenge.common.MainDispatcherRule
import com.example.marvelchallenge.core.toRight
import com.example.marvelchallenge.domain.GetCharacterInfoInteractor
import com.example.marvelchallenge.domain.GetComicsForCharacterInteractor
import com.example.marvelchallenge.domain.GetEventsForCharacterInteractor
import com.example.marvelchallenge.domain.GetSeriesForCharacterInteractor
import com.example.marvelchallenge.presentation.mapper.CharacterMapperUIModel
import com.example.marvelchallenge.presentation.mapper.ComicMapperUIModel
import com.example.marvelchallenge.presentation.mapper.EventMapperUIModel
import com.example.marvelchallenge.presentation.mapper.SeriesMapperUIModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import com.example.marvelchallenge.domain.model.Character
import com.example.marvelchallenge.domain.model.Comic
import com.example.marvelchallenge.domain.model.ComicDetails
import com.example.marvelchallenge.domain.model.Event
import com.example.marvelchallenge.domain.model.EventDetails
import com.example.marvelchallenge.domain.model.Series
import com.example.marvelchallenge.domain.model.SeriesDetails
import com.example.marvelchallenge.presentation.model.CharacterUIModel
import kotlinx.coroutines.flow.first
import org.junit.Rule
import org.mockito.kotlin.whenever
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`

@ExperimentalCoroutinesApi
class CharacterDetailsViewModelTest {

  private val testDispatcher = UnconfinedTestDispatcher()

  @get:Rule
  val mainDispatcherRule = MainDispatcherRule(testDispatcher)

  private val getCharacterDetailsInteractor: GetCharacterInfoInteractor = mock()
  private val getSeriesForCharacterInteractor: GetSeriesForCharacterInteractor = mock()
  private val getComicsForCharacterInteractor: GetComicsForCharacterInteractor = mock()
  private val getEventsForCharacterInteractor: GetEventsForCharacterInteractor = mock()

  private val characterMapperUIModel = CharacterMapperUIModel()
  private val comicMapperUIModel = ComicMapperUIModel()
  private val seriesMapperUIModel = SeriesMapperUIModel()
  private val eventMapperUIModel = EventMapperUIModel()

  private lateinit var viewModel: CharacterDetailsViewModel
  val characterId = 1
  val character = Character(
    id = characterId,
    name = "test",
    description = "test",
    thumbnailUrl = "test",
    comics = emptyList(),
    series = emptyList(),
    events = emptyList()
  )
  val series = Series(
    id = 1,
    name = "test",
    details = SeriesDetails(
      description = "test",
      thumbnailUrl = "test",
      startYear = "test",
      endYear = "test",
      rating = "test"
    )
  )
  val comic = Comic(
    id = 1,
    name = "test",
    details = ComicDetails(
      description = "test",
      thumbnailUrl = "test",
      pageCount = 1
    )
  )
  val event = Event(
    id = 1,
    name = "test",
    details = EventDetails(
      description = "test",
      thumbnailUrl = "test"
    )
  )
  val listOfComics = listOf(comic)
  val listOfSeries = listOf(series)
  val listOfEvents = listOf(event)
  val characterUIModel = CharacterUIModel(
    id = characterId,
    name = "test",
    thumbnailUrl = "test"
  )

  @Before
  fun setup() {
    viewModel = CharacterDetailsViewModel(
      getCharacterDetailsInteractor,
      getSeriesForCharacterInteractor,
      getComicsForCharacterInteractor,
      getEventsForCharacterInteractor,
      characterMapperUIModel,
      comicMapperUIModel,
      seriesMapperUIModel,
      eventMapperUIModel
    )
  }

  @Test
  fun `fetchCharacterDetails updates state correctly when interactor returns successfully`() =
    runTest {
      // Given

      whenever(getCharacterDetailsInteractor(characterId)).thenReturn(character.toRight())
      whenever(getComicsForCharacterInteractor(characterId)).thenReturn(listOfComics.toRight())
      whenever(getEventsForCharacterInteractor(characterId)).thenReturn(listOfEvents.toRight())
      whenever(getSeriesForCharacterInteractor(characterId)).thenReturn(listOfSeries.toRight())

      // When
      viewModel.fetchCharacterDetails(characterId)

      // Then
      val state = viewModel.characterDetailState.first { it.characterState is DetailsState.Success }

      assertThat(state.characterState, `is`(DetailsState.Success(listOf(characterUIModel))))
    }
}