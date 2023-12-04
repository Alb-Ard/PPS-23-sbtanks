package org.aas.sbtanks.enemies.spawn

import org.aas.sbtanks.behaviours.{CollisionBehaviour, PositionBehaviour}
import org.aas.sbtanks.physics.{AABB, PhysicsWorld}

import scala.annotation.tailrec




/**
 * A utility class responsible for providing positions for tanks in a game world,
 * taking into account collision behavior and the dimensions of the game board.
 *
 * @param width  The width of the game board.
 * @param height The height of the game board.
 * @param tank   The tank for which positions are provided, must implement `PositionBehaviour` and `CollisionBehaviour`.
 */
case class PositionProvider(width: Double, height: Double, strategy: PositionStrategy[(Double, Double), (Double, Double)])(tank: PositionBehaviour with CollisionBehaviour):

    /**
     * Checks whether a position is free from collisions (it use the PhysicWorld singleton).
     *
     * @param x The x-coordinate of the position to check.
     * @param y The y-coordinate of the position to check.
     * @return `true` if the position is free, `false` otherwise.
     */
    private def checkPosition(x: Double, y: Double) =
        PhysicsWorld.getBoxOverlaps(AABB(x, y, tank.boundingBox.width, tank.boundingBox.height), tank.layerMasks, Seq(tank)).isEmpty

    private def providePosition() =
        strategy.providePosition((width, height))

    /**
     * Finds the first free position for the tank, considering collision behavior within its own layermask.
     * Updates the tank's position accordingly.
     */
    private def findFreePosition(): (Double, Double) =

        @tailrec
        def loop(): (Double, Double) =
            val position@(x, y) = providePosition()
            if (checkPosition(x, y)) then
                position
            else
                loop()
        loop()

    /**
     * Finds the first free position for the tank, considering collision behavior within its own layermask.
     * Updates the tank's position accordingly.
     */
    def findFirstFreePosition() =
        val _@(x, y) = findFreePosition()
        PhysicsWorld.registerCollider(tank)
        tank.setPosition(x, y)


object PositionProvider:
    import org.aas.sbtanks.enemies.spawn.PositionStrategy.given
    def apply(width: Double, height: Double)(tank: PositionBehaviour with CollisionBehaviour) =
        new PositionProvider(width, height,  summon[PositionStrategy[(Double, Double), (Double, Double)]])(tank)



