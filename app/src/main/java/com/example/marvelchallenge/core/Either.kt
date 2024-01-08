package com.example.marvelchallenge.core

import com.example.marvelchallenge.core.Either.Left
import com.example.marvelchallenge.core.Either.Right

sealed class Either<out E, out V> {
  data class Left<out E>(val value: E) : Either<E, Nothing>()
  data class Right<out V>(val value: V) : Either<Nothing, V>()

  inline fun and(f: (V) -> Unit): Either<E, V> = when (this) {
    is Left -> this
    is Right -> {
      f(value)
      this
    }
  }

  inline fun <V2> map(f: (V) -> V2): Either<E, V2> = when (this) {
    is Left -> this
    is Right -> Right(f(value))
  }

  inline fun <E2> mapLeft(f: (E) -> E2): Either<E2, V> = when (this) {
    is Left -> Left(f(value))
    is Right -> this
  }

  inline fun <A> fold(
    ifLeft: (E) -> A,
    ifRight: (V) -> A,
  ): A = when (this) {
    is Left -> ifLeft(value)
    is Right -> ifRight(value)
  }



  inline fun onLeft(action: (E) -> Unit): Either<E, V> =
    also { if (it is Left) action(it.value) }

  inline fun onRight(action: (V) -> Unit): Either<E, V> =
    also { if (it is Right) action(it.value) }
}

fun <E> E.toLeft() = Left(this)

fun <V> V.toRight() = Right(this)

inline infix fun <E, V, V2> Either<E, V>.flatMap(f: (V) -> Either<E, V2>) = when (this) {
  is Left -> this
  is Right -> f(value)
}

inline infix fun <E, E2, V> Either<E, V>.flatMapLeft(f: (E) -> Either<E2, V>) = when (this) {
  is Left -> f(value)
  is Right -> this
}

inline fun <E, V1, V2, V3> Either<E, V1>.with(
  other: Either<E, V2>,
  f: (V1, V2) -> Either<E, V3>,
) = this.flatMap { result1 ->
  other.flatMap { result2 ->
    f(result1, result2)
  }
}

inline fun <E, V1, V2, V3, V4> Either<E, V1>.with(
  other1: Either<E, V2>,
  other2: Either<E, V3>,
  f: (V1, V2, V3) -> Either<E, V4>,
) = this.flatMap { result1 ->
  other1.flatMap { result2 ->
    other2.flatMap { result3 ->
      f(result1, result2, result3)
    }
  }
}

inline fun <E, V> Either<E, V>.getOrElse(f: (E) -> Either<E, V>): Either<E, V> =
  when (this) {
    is Right -> this
    is Left -> f(value)
  }

fun <E, V> Either<E, V>.getOrNull(): V? =
  when (this) {
    is Right -> this.value
    is Left -> null
  }
