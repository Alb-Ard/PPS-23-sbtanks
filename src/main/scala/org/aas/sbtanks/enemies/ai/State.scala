package org.aas.sbtanks.enemies.ai

import org.aas.sbtanks.enemies.ai.State.{EnemyMoves, EnemyState, EnemyStateMonad}


object State:

    trait Functor[F[_]]:
        def map[A, B](fa: F[A])(f: A => B): F[B]

    trait Monad[F[_]] extends Functor[F]:
        def pure[A](a: A): F[A]
        def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B]
        override def map[A, B](fa: F[A])(f: A => B): F[B] =
            flatMap(fa)(a => pure(f(a)))


    case class State[S, A](runAndTranslate: S => (A, S)):
        def map[B](f: A => B): State[S, B] =
            State(s0 =>
                val (a, s1) = runAndTranslate(s0)
                (f(a), s1)
            )

        def flatMap[B](f: A => State[S, B]): State[S, B] =
            State(s0 =>
                val (a, s1) = runAndTranslate(s0)
                f(a).runAndTranslate(s1)
            )


    opaque type EnemyState[A] = State[EnemyMoves, A]

    object EnemyStateMonad extends Monad[EnemyState]:
        def pure[A](a: A): EnemyState[A] = State(s => (a, s))
        def flatMap[A, B](state: EnemyState[A])(f: A => EnemyState[B]): EnemyState[B] = state.flatMap(f)

        def getState[A]: EnemyState[EnemyMoves] = State(s => (s, s))





    enum EnemyMoves:
        case MoveBottomY, MoveTowardsCenterX, MoveFarFromCenterX, MoveTopY




object a extends App:
    import org.aas.sbtanks.enemies.ai.State.EnemyMoves.*











