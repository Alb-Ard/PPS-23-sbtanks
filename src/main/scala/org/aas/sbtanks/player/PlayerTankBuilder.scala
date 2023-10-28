package org.aas.sbtanks.player

import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.entities.tank.factories.TankTypeData
import org.aas.sbtanks.behaviours.PositionBehaviour
import org.aas.sbtanks.behaviours.DirectionBehaviour
import org.aas.sbtanks.behaviours.CollisionBehaviour
import org.aas.sbtanks.behaviours.ConstrainedMovementBehaviour
import org.aas.sbtanks.physics.CollisionLayer

case class PlayerTankBuilder(private val tankTypeData: TankTypeData = PlayerTankData,
    private val x: Double = 0, 
    private val y: Double = 0,
    private val collisionSizeX: Double = 1,
    private val collisionSizeY: Double = 1):

    def setTankType(tankType: TankTypeData) =
        copy(tankTypeData = tankType)    

    def setPosition(x: Double = x, y: Double = y) =
        copy(x = x, y = y)

    def setCollisionSize(x: Double = x, y: Double = y) =
        copy(collisionSizeX = x, collisionSizeY = y)

    def build() =
        new Tank(tankTypeData)
            with PositionBehaviour(x, y)
            with ConstrainedMovementBehaviour
            with DirectionBehaviour
            with CollisionBehaviour(collisionSizeX, collisionSizeY, CollisionLayer.TanksLayer, Seq(
                CollisionLayer.BulletsLayer,
                CollisionLayer.WallsLayer,
                CollisionLayer.NonWalkableLayer
            ))
