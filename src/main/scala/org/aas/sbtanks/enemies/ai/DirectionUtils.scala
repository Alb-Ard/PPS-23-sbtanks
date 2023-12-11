package org.aas.sbtanks.enemies.ai

import scala.util.Random

/**
 * Utility object for handling directions in a 2D space as type aliases.
 */
object DirectionUtils:
    

    type Direction = (Double, Double)

    val Bottom: (Double, Double) = (0.0, 1.0)
    val Right: (Double, Double) = (1.0, 0.0)
    val Left: (Double, Double) = (-1.0, 0.0)
    val Top: (Double, Double) = (0.0, -1.0)

    val NoDirection: (Double, Double) = (0.0, 0.0)

