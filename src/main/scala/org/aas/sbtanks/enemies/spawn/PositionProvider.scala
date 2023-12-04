package org.aas.sbtanks.enemies.spawn

import org.aas.sbtanks.behaviours.{CollisionBehaviour, PositionBehaviour}
import org.aas.sbtanks.physics.{AABB, PhysicsContainer}

import scala.annotation.tailrec
import org.aas.sbtanks.physics.CollisionLayer

/**
 * A utility class responsible for providing positions for tanks in a game world,
 * taking into account collision behavior and the dimensions of the game board.
 *
 * @param width             The width of the game board.
 * @param height            The height of the game board.
 * @param collisionMask     The mask to use when checking free positions
 * @param collisionSizeX    The width to use when checking free positions
 * @param collisionSizeY    The hieght to use when checking free positions
 */
case class PositionProvider(width: Double, height: Double, strategy: PositionStrategy[(Double, Double), (Double, Double)])(collisionMask: Seq[CollisionLayer], collisionSizeX: Double, collisionSizeY: Double)(using physics: PhysicsContainer):
    /**
     * Checks whether a position is free from collisions (it use the PhysicWorld singleton).
     *
     * @param x The x-coordinate of the position to check.
     * @param y The y-coordinate of the position to check.
     * @return `true` if the position is free, `false` otherwise.
     */
    private def isPositionAvailable(x: Double, y: Double) =
        physics.getBoxOverlaps(AABB(x, y, collisionSizeX, collisionSizeY), collisionMask, Seq.empty).isEmpty

    private def providePosition() =
        strategy.providePosition((width, height))

    /**
     * Finds the first free position for the tank, considering collision behavior within its own layermask.
     * Updates the tank's position accordingly.
     */
    def findFreePosition(): Option[(Double, Double)] =
        @tailrec
        def loop(): Option[(Double, Double)] =
            providePosition() match
                case None => None
                case Some(position@(x, y)) =>
                    isPositionAvailable(x, y) match
                        case true => Option(position)
                        case _ => loop()
        loop()


object PositionProvider:
    import org.aas.sbtanks.enemies.spawn.PositionStrategy.given
    def apply(using physics: PhysicsContainer)(width: Double, height: Double) =
        val strategy = summon[PositionStrategy[(Double, Double), (Double, Double)]].reset((width, height))
        (collisionMask: Seq[CollisionLayer], collisionSizeX: Double, collisionSizeY: Double) =>
            new PositionProvider(width, height, strategy)(collisionMask, collisionSizeX, collisionSizeY)



