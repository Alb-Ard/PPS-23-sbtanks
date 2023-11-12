package org.aas.sbtanks.enemies.ai

import scala.util.Random

object EnemyUtils:

    enum EnemyDirection:
        case BottomY, RightX, LeftX, TopY

    type Direction = (Double, Double)

    val Bottom_Y: (Double, Double) = (0.0, 1.0)
    val Right_X: (Double, Double) = (1.0, 0.0)
    val Left_X: (Double, Double) = (0.0, -1.0)
    val Top_Y: (Double, Double) = (-1.0, 0.0)


    case class Enemy(move: EnemyDirection, position: (Double, Double))

    /* TODO check world (probability is a temporary check)*/
    def isMoveValid(enemy: Enemy): Boolean =
        val random = new Random()
        val probability = 0.1

        random.nextDouble() < probability