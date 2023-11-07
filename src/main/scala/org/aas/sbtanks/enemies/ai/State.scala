package org.aas.sbtanks.enemies.ai

import org.aas.sbtanks.enemies.ai.State.EnemyStateMonad.{checkMove, computeState, moveNext, updateCoord}

import scala.util.Random


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


        def withFilter(p: A => Boolean): State[S, Option[A]] =
            State(s0 =>
                val (a, s1) = runAndTranslate(s0)
                if (p(a)) (Some(a), s1) else (None, s0)
            )


    type EnemyState[A] = State[Enemy, A]

    object EnemyStateMonad extends Monad[EnemyState]:
        def run[A](state: EnemyState[A]): Enemy => (A, Enemy) = state.runAndTranslate

        override def pure[A](a: A): EnemyState[A] = State(s => (a, s))
        override def flatMap[A, B](state: EnemyState[A])(f: A => EnemyState[B]): EnemyState[B] = state.flatMap(f)

        def getState: EnemyState[Enemy] = State(s => (s, s))


        def gets[A](f: Enemy => A): EnemyState[A] = State(s => (f(s), s))

        def setState(s: Enemy): EnemyState[Unit] = State(_ => ((), s))

        def modify[A](f: Enemy => Enemy): EnemyState[Unit] = for
            s <- getState
            _ <- setState(f(s))
        yield ()


        /*
            check if an EnemyDirection is valid, return Optional State
         */
        def checkMove(): EnemyState[Option[EnemyDirection]] =
            for
                s0 <- getState
                c <- gets(_.move)
                if isMoveValid(s0)
            yield c



        /*
            try the different direction with a specific priority, till it finds the first direction whose move is permitted
         */
        def moveNext(): EnemyState[EnemyDirection] =
            for
                c <- checkMove()
                d <- c match
                    case Some(d) => pure(d)
                    case None => modify(s => s.copy(move = nextDirectionPriority(s.move))).flatMap(_ => moveNext())
            yield d


        /*
            update the coordinates based on the actual direction
         */
        def updateCoord(): EnemyState[Unit] =
            for
                d <- moveNext()
                c <- gets(_.position)
                - <- modify(s => s.copy(position = d match
                        case EnemyDirection.BottomY => (c._1, c._2 + 1)
                        case EnemyDirection.RightX => (c._1 + 1, c._2)
                        case EnemyDirection.LeftX => (c._1 - 1, c._2)
                        case EnemyDirection.TopY => (c._1, c._2 - 1)
                ))
            yield ()


        def computeState(): EnemyState[(Int, Int)] =
            for
                - <- updateCoord()
                c2 <- gets(_.position)
            yield c2












object EnemyUtils:

    enum EnemyDirection:
        case BottomY, RightX, LeftX, TopY

    def nextDirectionPriority(direction: EnemyDirection): EnemyDirection = direction match
        case EnemyDirection.BottomY => EnemyDirection.RightX
        case EnemyDirection.RightX => EnemyDirection.LeftX
        case EnemyDirection.LeftX => EnemyDirection.TopY
        case EnemyDirection.TopY => EnemyDirection.BottomY






    case class Enemy(move: EnemyDirection, position: (Int, Int))

    /* TODO check world (probability is a temporary check)*/
    def isMoveValid(enemy: Enemy): Boolean =
        val random = new Random()
        val probability = 0.1

        random.nextDouble() < probability







object a extends App:
    import org.aas.sbtanks.enemies.ai.EnemyUtils.*
    import org.aas.sbtanks.enemies.ai.State.EnemyStateMonad

    //println(moveNext(Enemy(EnemyDirection.BottomY, (0, 0))).runAndTranslate(Enemy(EnemyDirection.BottomY, (0, 0))))

    println(computeState().runAndTranslate(Enemy(EnemyDirection.BottomY, (0, 0))))



























