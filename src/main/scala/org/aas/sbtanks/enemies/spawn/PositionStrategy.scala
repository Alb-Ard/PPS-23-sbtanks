package org.aas.sbtanks.enemies.spawn

import scala.util.Random

/**
 * A trait representing a strategy for providing positions in a game world.
 *
 * @tparam A The type of return for the position to be provided.
 * @tparam C The type of context used to determine the position.
 */
trait PositionStrategy[+A, C]:
    /**
     * Provides a position based on the given context.
     *
     * @param context The context used to determine the position.
     * @return The provided position.
     */
    def providePosition(context: C): A

/**
 * A companion object for the `PositionStrategy` trait containing predefined strategies.
 */
object PositionStrategy:

    /**
     * A given instance of `PositionStrategy` that provides positions in the upper half of the screen.
     * The position is represented as a tuple of (Double, Double), and the context is a tuple of (Double, Double)
     * representing the width and height of a rectangular game world.
     */
    given upperHalfPositionStrategy: PositionStrategy[(Double, Double), (Double, Double)] with
        def providePosition(context: (Double, Double)): (Double, Double) =
            (Random.nextInt(context(0).toInt).toDouble, Random.nextInt((context(1) / 2).toInt).toDouble)




