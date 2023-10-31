package org.aas.sbtanks.player

import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.entities.tank.factories.TankTypeData
import org.aas.sbtanks.behaviours.PositionBehaviour
import org.aas.sbtanks.behaviours.DirectionBehaviour
import org.aas.sbtanks.behaviours.CollisionBehaviour
import org.aas.sbtanks.behaviours.ConstrainedMovementBehaviour
import org.aas.sbtanks.physics.CollisionLayer

case class PlayerTank() extends Tank(PlayerTankData)

case class PlayerTankBuilder(private val x: Double = 0, 
    private val y: Double = 0,
    private val collisionSizeX: Double = 1,
    private val collisionSizeY: Double = 1,
    private val collisionLayer: CollisionLayer = CollisionLayer.TanksLayer,
    private val collisionMask: Set[CollisionLayer] = PlayerTankBuilder.DEFAULT_COLLISION_MASK):

    def setPosition(x: Double = x, y: Double = y) =
        copy(x = x, y = y)

    def setCollisionSize(x: Double = x, y: Double = y) =
        copy(collisionSizeX = x, collisionSizeY = y)

    def setCollisionLayer(layer: CollisionLayer) =
        copy(collisionLayer = layer)

    def setCollisionMaskLayer(layer: CollisionLayer, active: Boolean = true) =
        copy(collisionMask = active match
            case true => collisionMask + layer
            case _ => collisionMask.filterNot(layer.equals)
        )

    def build() =
        new PlayerTank()
            with PositionBehaviour(x, y)
            with ConstrainedMovementBehaviour
            with DirectionBehaviour
            with CollisionBehaviour(collisionSizeX, collisionSizeY, collisionLayer, collisionMask.toSeq)


object PlayerTankBuilder:
    val DEFAULT_COLLISION_MASK: Set[CollisionLayer] = Set(
        CollisionLayer.BulletsLayer,
        CollisionLayer.WallsLayer,
        CollisionLayer.NonWalkableLayer
    )