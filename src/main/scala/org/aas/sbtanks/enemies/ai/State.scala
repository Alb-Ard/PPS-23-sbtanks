package org.aas.sbtanks.enemies.ai

import org.aas.sbtanks.enemies.ai.State.EnemyStateMonad.moveNext


object State:
    import org.aas.sbtanks.enemies.ai.EnemyUtils.*

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


    type EnemyState[A] = State[Enemy, A]

    object EnemyStateMonad extends Monad[EnemyState]:
        def run[A](state: EnemyState[A]): Enemy => (A, Enemy) = state.runAndTranslate

        override def pure[A](a: A): EnemyState[A] = State(s => (a, s))
        override def flatMap[A, B](state: EnemyState[A])(f: A => EnemyState[B]): EnemyState[B] = state.flatMap(f)

        def getState[A]: EnemyState[Enemy] = State(s => (s, s))

        def setState[A](s: Enemy): EnemyState[Unit] = State(_ => ((), s))

        def modify[A](f: Enemy => Enemy): EnemyState[Unit] = for
            s <- getState
            _ <- setState(f(s))
        yield ()


        def moveNext(enemy: Enemy): EnemyState[Enemy] =
            for
                newMove <- if isMoveValid(enemy) then pure(enemy) else moveNext(enemy.copy(move = nextDirectionPriority(enemy.move)))
            yield newMove



object EnemyUtils:

    enum EnemyDirection:
        case BottomY, RightX, LeftX, TopY

    def nextDirectionPriority(direction: EnemyDirection): EnemyDirection = direction match
        case EnemyDirection.BottomY => EnemyDirection.RightX
        case EnemyDirection.RightX => EnemyDirection.LeftX
        case EnemyDirection.LeftX => EnemyDirection.TopY
        case EnemyDirection.TopY => EnemyDirection.BottomY



    case class Enemy(move: EnemyDirection, position: (Int, Int))

    /* TODO check world */
    def isMoveValid(enemy: Enemy): Boolean = true







object a extends App:
    import org.aas.sbtanks.enemies.ai.EnemyUtils.*
    import org.aas.sbtanks.enemies.ai.State.EnemyStateMonad

    //println(moveNext(Enemy(EnemyDirection.BottomY, (0, 0))).runAndTranslate(Enemy(EnemyDirection.BottomY, (0, 0))))
























