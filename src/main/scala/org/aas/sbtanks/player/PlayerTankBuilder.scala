package org.aas.sbtanks.player

import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.behaviours.PositionBehaviour
import org.aas.sbtanks.behaviours.SteppedMovementDirectionBehaviour
import org.aas.sbtanks.behaviours.CollisionBehaviour
import org.aas.sbtanks.behaviours.ConstrainedMovementBehaviour
import org.aas.sbtanks.physics.CollisionLayer
import org.aas.sbtanks.entities.tank.factories.TankTypeData

case class PlayerTankBuilder(private val tankTypeData: TankTypeData = PlayerTankData, private val x: Double = 0, private val y: Double = 0):
    def setPosition(x: Double = x, y: Double = y) =
        copy(x = x, y = y)
    
    def build() =
        new Tank(tankTypeData)
            with PositionBehaviour(x, y)
            with ConstrainedMovementBehaviour 
            with SteppedMovementDirectionBehaviour(PlayerTankData().speed)
            with CollisionBehaviour(16, 16, CollisionLayer.TanksLayer, Seq(CollisionLayer.BulletsLayer, CollisionLayer.WallsLayer))
