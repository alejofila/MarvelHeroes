package com.example.marvelchallenge.presentation.mapper

import com.example.marvelchallenge.domain.model.Character
import com.example.marvelchallenge.presentation.model.CharacterUIModel
import javax.inject.Inject

class CharacterMapperUIModel @Inject constructor(){
  fun mapToUIModel(character: Character): CharacterUIModel {
    return with(character) {
      CharacterUIModel(
        id = id,
        name = name,
        thumbnailUrl = character.thumbnailUrl
      )
    }
  }
}