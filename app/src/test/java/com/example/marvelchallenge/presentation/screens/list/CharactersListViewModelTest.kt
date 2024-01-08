package com.example.marvelchallenge.presentation.screens.list

import com.example.marvelchallenge.common.MainDispatcherRule
import com.example.marvelchallenge.core.toLeft
import com.example.marvelchallenge.core.toRight
import com.example.marvelchallenge.domain.GetCharactersInteractor
import com.example.marvelchallenge.presentation.mapper.CharacterMapperUIModel
import com.example.marvelchallenge.presentation.mapper.CharacterListUIErrorMapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import com.example.marvelchallenge.domain.model.Character
import com.example.marvelchallenge.domain.repository.DomainError
import com.example.marvelchallenge.presentation.model.CharacterUIModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.junit.Rule
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class CharactersListViewModelTest {

  private val testDispatcher = UnconfinedTestDispatcher()

  private val domainCharacter = Character(
    id = 1,
    name = "test",
    description = "test",
    thumbnailUrl = "test",
    comics = emptyList(),
    series = emptyList(),
    events = emptyList()
  )
  private val domainCharacter2 = Character(
    id = 2,
    name = "test",
    description = "test",
    thumbnailUrl = "test",
    comics = emptyList(),
    series = emptyList(),
    events = emptyList()
  )
  private val listOfDomainCharacters = listOf(domainCharacter)

  private val uiCharacter = CharacterUIModel(
    id = 1,
    name = "test",
    thumbnailUrl = "test"
  )
  private val uiCharacter2 = CharacterUIModel(
    id = 2,
    name = "test",
    thumbnailUrl = "test"
  )
  private val listOfUiCharacters = listOf(uiCharacter)

  @get:Rule
  val mainDispatcherRule = MainDispatcherRule(testDispatcher)

  private val getCharactersInteractor: GetCharactersInteractor = mock()
  private val mapper = CharacterMapperUIModel()
  private val errorMapper = CharacterListUIErrorMapper()

  private lateinit var viewModel: CharactersListViewModel

  @Before
  fun setup() {
    viewModel = CharactersListViewModel(mapper, errorMapper, getCharactersInteractor)
  }

  @Test
  fun `Given a query string, when queryCharacters is called,then state should update with success status`() =
    runTest {
      // Given

      whenever(getCharactersInteractor("", 0)).thenReturn(listOfDomainCharacters.toRight())

      // When
      viewModel.queryCharacters("")

      // Then
      val state =
        viewModel.characterScreenUIState.first { it.characterListState is CharacterListState.Success && it.characterListState.characters.isNotEmpty() }
      assertThat(state.isLoading, `is`(false))
      assertThat(state.searchQuery, `is`(""))
      assertThat(
        state.characterListState,
        `is`(
          CharacterListState.Success(
            listOfUiCharacters
          )
        )
      )

    }

  @Test
  fun `Given a server error, when queryCharacters is called, then it should update the state correctly`() =
    runTest {
      // Given
      whenever(
        getCharactersInteractor(
          "",
          0
        )
      ).thenReturn(
        DomainError.ServerError.toLeft()
      )

      // When
      viewModel.queryCharacters("")

      // Then
      val state = viewModel.characterScreenUIState.first {
        it.characterListState is CharacterListState.Error
      }

      assertThat(
        state.characterListState,
        `is`(CharacterListState.Error("There was an error trying to fetch characters, try again later"))
      )
      assertThat(state.searchQuery, `is`(""))
      assertThat(state.isLoading, `is`(false))
    }

  @Test
  fun `Given page, when queryNextPage is called, then state should be Successful and it should contain every character`() =
    runTest {
      // Given
      val listOfDomainCharactersPage2 = listOf(domainCharacter, domainCharacter2)
      whenever(getCharactersInteractor("", 1)).thenReturn(listOfDomainCharactersPage2.toRight())


      viewModel.queryNextPage()

      // Then
      val state =
        viewModel.characterScreenUIState.first { it.characterListState is CharacterListState.Success && it.characterListState.characters.isNotEmpty() }
      assertThat(state.isLoading, `is`(false))
      assertThat(state.searchQuery, `is`(""))
      assertThat(
        state.characterListState,
        `is`(
          CharacterListState.Success(
            listOf(
              uiCharacter,
              uiCharacter2,
            )
          )
        )
      )
    }

  @Test
  fun `Given consecutive calls to queryCharacters , when queryCharacters is called three times in a row, then getCharactersInteractor should be called single time with the last query string`() =
    runTest {
      // Given
      whenever(getCharactersInteractor("cap", 0)).thenReturn(listOfDomainCharacters.toRight())

      // When
      viewModel.queryCharacters("c")
      viewModel.queryCharacters("ca")
      viewModel.queryCharacters("cap")

      // Then
      viewModel.characterScreenUIState.first { it.characterListState is CharacterListState.Success && it.characterListState.characters.isNotEmpty() }
      verify(getCharactersInteractor, times(1)).invoke("cap", 0)

      // Check that no more calls were made to getCharactersInteractor
      verifyNoMoreInteractions(getCharactersInteractor)
    }
}