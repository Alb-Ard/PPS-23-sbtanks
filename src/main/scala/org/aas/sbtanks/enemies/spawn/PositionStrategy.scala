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
      * Resets this strategy before starting a new search.
      *
      * @param context The context that will be used to determine the position.
      * @return This resetted strategy.
      */
    def reset(context: C): this.type = this

    /**
     * Provides a position based on the given context.
     *
     * @param context The context used to determine the position.
     * @return The provided position.
     */
    def providePosition(context: C): Option[A]

/**
 * A companion object for the `PositionStrategy` trait containing predefined strategies.
 */
object PositionStrategy:
    private type Position = (Double, Double)
    private type AreaContext = (Double, Double)

    /**
     * A given instance of `PositionStrategy` that provides positions in the upper half of the screen.
     * The position is represented as a tuple of (Double, Double), and the context is a tuple of (Double, Double)
     * representing the width and height of a rectangular game world.
     */
    given upperHalfPositionStrategy: PositionStrategy[Position, AreaContext] with
        private var remainingPositions = Seq.empty[Position]

        override def reset(context: (Double, Double)) = 
            remainingPositions = (0 until context(0).toInt / 2).flatMap(y => (0 until context(1).toInt).map(x => (x, y)))
                .map((x, y) => (x.toDouble, y.toDouble))
            this
            
        override def providePosition(context: (Double, Double)): Option[(Double, Double)] =
            remainingPositions.length match
                case 0 => None
                case n =>          
                    val positionIndex = Random.nextInt(n)
                    val position = remainingPositions(positionIndex)
                    remainingPositions = remainingPositions.filterNot(p => p == position)
                    Option(position)
            




