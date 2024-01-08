package com.example.marvelchallenge.data.remote

import com.example.marvelchallenge.data.remote.model.CharactersResponse
import com.example.marvelchallenge.data.remote.model.ComicsResponse
import com.example.marvelchallenge.data.remote.model.EventsResponse
import com.example.marvelchallenge.data.remote.model.SeriesResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

private const val API_KEY = "a901d2ed4c486a86a4dae012cd20e7ec"

const val PAGE_LIMIT = 10

interface MarvelApi {

  @Headers(
    "Referer:developer.marvel.com"
  )
  @GET("characters")
  suspend fun getCharacters(
    @Query("nameStartsWith") nameStartsWith: String?,
    @Query("limit") limit: Int = PAGE_LIMIT,
    @Query("offset") offset: Int = 0,
    @Query("apikey") apikey: String = API_KEY,
  ): CharactersResponse

  @Headers(
    "Referer:developer.marvel.com"
  )
  @GET("characters/{characterId}/comics")
  suspend fun getComicsForCharacter(
    @Path("characterId") characterId: Int,
    @Query("limit") limit: Int = PAGE_LIMIT,
    @Query("apikey") apikey: String = API_KEY,
  ): ComicsResponse

  @Headers(
    "Referer:developer.marvel.com"
  )
  @GET("characters/{characterId}/series")
  suspend fun getSeriesForCharacter(
    @Path("characterId") characterId: Int,
    @Query("limit") limit: Int = PAGE_LIMIT,
    @Query("apikey") apikey: String = API_KEY,
  ): SeriesResponse

  @Headers(
    "Referer:developer.marvel.com"
  )
  @GET("characters/{characterId}/events")
  suspend fun getEventsForCharacter(
    @Path("characterId") characterId: Int,
    @Query("limit") limit: Int = PAGE_LIMIT,
    @Query("apikey") apikey: String = API_KEY,
  ): EventsResponse
}