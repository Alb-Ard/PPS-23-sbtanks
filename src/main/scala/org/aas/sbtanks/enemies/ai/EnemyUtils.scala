package org.aas.sbtanks.enemies.ai

import scala.util.Random

object EnemyUtils:

    enum EnemyDirection:
        case BottomY, RightX, LeftX, TopY


    case class Enemy(move: EnemyDirection, position: (Double, Double))

    /* TODO check world (probability is a temporary check)*/
    def isMoveValid(enemy: Enemy): Boolean =
        val random = new Random()
        val probability = 0.1

        random.nextDouble() < probability