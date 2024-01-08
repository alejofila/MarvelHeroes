package com.example.marvelchallenge.domain

import com.example.marvelchallenge.core.toLeft
import com.example.marvelchallenge.core.toRight
import com.example.marvelchallenge.domain.model.Character
import com.example.marvelchallenge.domain.model.Event
import com.example.marvelchallenge.domain.model.EventDetails
import com.example.marvelchallenge.domain.repository.DomainError
import com.example.marvelchallenge.domain.repository.EventsRepository
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class GetEventsForCharacterInteractorTest {
  private val eventRepository: EventsRepository = mock()
  private lateinit var getEventsForCharacterInteractor: GetEventsForCharacterInteractor
  private val character =
    Character(1, "Test", "Test", "Test", emptyList(), emptyList(), emptyList())

  @Before
  fun setup() {
    getEventsForCharacterInteractor = GetEventsForCharacterInteractor(
      eventRepository
    )
  }

  @Test
  fun `getEventDetails returns character with correct events`() = runTest {
    val events = listOf(
      Event(
        1, "Test", details = EventDetails(
          "Test", "Test",
        )
      )
    )
    whenever(eventRepository.getDataForCharacter(character.id)).thenReturn(events.toRight())

    val result = getEventsForCharacterInteractor(character.id)

    assertThat(events.toRight(), `is`(result))
  }

  @Test
  fun `getEventDetails returns error when repository fails`() = runTest {
    whenever(eventRepository.getDataForCharacter(character.id)).thenReturn(DomainError.ServerError.toLeft())

    val result = getEventsForCharacterInteractor(character.id)

    assertThat(DomainError.ServerError.toLeft(), `is`(result))
  }
}