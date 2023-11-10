package org.aas.sbtanks.enemies.ai

import org.aas.sbtanks.enemies.ai.AiMovement.computeState
import org.aas.sbtanks.enemies.ai.EnemyUtils.*
import org.aas.sbtanks.enemies.ai.State.{EnemyState, State}

type AiMovementState[A] = State[AiEntity, A]


object AiMovement:
    def run[A](state: AiMovementState[A]): AiEntity => (A, AiEntity) = state.runAndTranslate

    def pure[A](a: A): AiMovementState[A] = State(s => (a, s))



    def getState: AiMovementState[AiEntity] = State(s => (s, s))


    def gets[A](f: AiEntity => A): AiMovementState[A] = State(s => (f(s), s))


    def setState(s: AiEntity): AiMovementState[Unit] = State(_ => ((), s))

    def modify[A](f: AiEntity => AiEntity): AiMovementState[Unit] = for
        s <- getState
        _ <- setState(f(s))
    yield ()

    def isMoveValid(entity: AiEntity): Boolean =
        entity.testMoveRelative(entity.directionX, entity.directionY)



    def checkMove(): AiMovementState[Option[(Double, Double)]] =
        for
            s0 <- getState
            x <- gets(x => (x.directionX.asInstanceOf[Double], x.directionY.asInstanceOf[Double]))
            if isMoveValid(s0)
        yield x

    def moveNext(): AiMovementState[(Double, Double)] =
        for
            c <- checkMove()
            d <- c match
                case Some(d) => pure(d)
                case None => transition(false).flatMap(_ => moveNext())
        yield d

    def getNewCoord(): AiMovementState[(Double, Double)] =
        for
            d <- moveNext()
            c <- gets(x => (x.positionX, x.positionY))
            newPos <- d match
                case Bottom_Y => pure((c._1, c._2 + 1))
                case Right_X => pure((c._1 + 1, c._2))
                case Left_X => pure((c._1 - 1, c._2))
                case Top_Y => pure((c._1, c._2 - 1))
        yield newPos


    def computeState(): AiMovementState[(Double, Double)] =
        for
            newCoord <- getNewCoord()
            _ <- transition(true)
        yield newCoord


    def transition(value: Boolean): AiMovementState[Unit] =
        for
            dir <- gets(x => (x.directionX.asInstanceOf[Double], x.directionY.asInstanceOf[Double]))
            newDir <- (dir, value) match
                case ((_, y), true) if y > 0.0 => pure(Bottom_Y)
                case ((_, y), false) if y > 0.0 => pure(Right_X)

                case ((x, _), true) if x > 0.0 => pure(Bottom_Y)
                case ((x, _), false) if x > 0.0 => pure(Left_X)

                case ((x, _), true) if x < 0.0=> pure(Bottom_Y)
                case ((x, _), false) if x < 0.0 => pure(Top_Y)

                case ((_, y), true) if y < 0.0 => pure(Left_X)
                case ((_, y), false) if y < 0.0 => pure(Bottom_Y)

            _ <- modify(e => e.setDirection(newDir._1, newDir._2).asInstanceOf[AiEntity])
        yield
            ()

object aa extends App:

    def computeAiState(entity: AiEntity) =
        computeState().runAndTranslate(entity)

