package com.example.marvelchallenge.data.mapper

import com.example.marvelchallenge.data.remote.model.MarvelCharacter
import com.example.marvelchallenge.domain.model.Character
import javax.inject.Inject

class CharacterDTOMapper @Inject constructor(
  private val comicDTOMapper: ComicDTOMapper,
  private val seriesDetailsDTOMapper: SeriesDetailsDTOMapper,
  private val eventDTOMapper: EventDTOMapper
) {
  fun mapToDomain(marvelCharacter: MarvelCharacter): Character {
    return Character(
      id = marvelCharacter.id,
      name = marvelCharacter.name,
      description = marvelCharacter.description,
      thumbnailUrl = "${marvelCharacter.thumbnail.path}.${marvelCharacter.thumbnail.extension}",
      comics = marvelCharacter.comics.items.map { comicDTOMapper.mapToDomain(it) },
      series = marvelCharacter.series.items.map { seriesDetailsDTOMapper.mapToDomain(it) },
      events = marvelCharacter.events.items.map { eventDTOMapper.mapToDomain(it) }
    )
  }
}