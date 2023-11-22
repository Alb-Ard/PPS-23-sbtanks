package org.aas.sbtanks.enemies.spawn

import org.aas.sbtanks.behaviours.{CollisionBehaviour, PositionBehaviour}
import org.aas.sbtanks.entities.tank.controller.TankController.ControllableTank
import org.aas.sbtanks.physics.{AABB, PhysicsWorld}

import scala.util.Random

/**
 * A utility class responsible for providing positions for tanks in a game world,
 * taking into account collision behavior and the dimensions of the game board.
 *
 * @param width  The width of the game board.
 * @param height The height of the game board.
 * @param tank   The tank for which positions are provided, must implement `PositionBehaviour` and `CollisionBehaviour`.
 */
case class PositionProvider(width: Double, height: Double)(tank: PositionBehaviour with CollisionBehaviour):

    /**
     * Checks whether a position is free from collisions (it use the PhysicWorld singleton).
     *
     * @param x The x-coordinate of the position to check.
     * @param y The y-coordinate of the position to check.
     * @return `true` if the position is free, `false` otherwise.
     */
    private def checkPosition(x: Double, y: Double) =
        PhysicsWorld.getBoxOverlaps(AABB(x, y, width, height), tank.layerMasks, Seq(tank)).isEmpty

    private def providePosition() =
        (math.round(Random.nextDouble() * width), math.round(Random.nextDouble() * height))

    /**
     * Finds the first free position for the tank, considering collision behavior within its own layermask.
     * Updates the tank's position accordingly.
     */
    def findFirstFreePosition() =
        var position = providePosition()
        while !checkPosition(position(0), position(1)) do
            position = providePosition()
        tank.setPosition(position(0), position(1))


object PositionProvider:
    def apply(width: Double, height: Double)(tank: PositionBehaviour with CollisionBehaviour) = new PositionProvider(width, height)(tank)



