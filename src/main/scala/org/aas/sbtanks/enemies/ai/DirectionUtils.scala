package org.aas.sbtanks.enemies.ai

import scala.util.Random

object DirectionUtils:
    

    type Direction = (Double, Double)

    val Bottom_Y: (Double, Double) = (0.0, 1.0)
    val Right_X: (Double, Double) = (1.0, 0.0)
    val Left_X: (Double, Double) = (0.0, -1.0)
    val Top_Y: (Double, Double) = (-1.0, 0.0)

