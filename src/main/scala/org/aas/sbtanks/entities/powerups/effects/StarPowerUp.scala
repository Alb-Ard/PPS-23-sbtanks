package org.aas.sbtanks.entities.powerups.effects

import org.aas.sbtanks.behaviours.PositionBehaviour
import org.aas.sbtanks.behaviours.DirectionBehaviour
import org.aas.sbtanks.entities.powerups.PowerUp.ContextualFuncPowerUp
import org.aas.sbtanks.entities.powerups.contexts.{CachedContext, CounterContext, MapContext}
import org.aas.sbtanks.entities.tank.behaviours.TankMultipleShootingBehaviour
import org.aas.sbtanks.entities.tank.factories.PowerTankData
import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.entities.tank.structure.Tank.{BasicTank, PowerTank}
import org.aas.sbtanks.entities.powerups.PowerUp.PowerUpConstraint
import org.aas.sbtanks.player.PlayerTank


object Star:
    import StarPowerUpUtils.*

    /**
     * Case class representing the Star power-up instance, which is a functional power-up for tanks with a specific constraint on its use a time effect and a context to support its appliance
     */
    case class StarPowerUp() 
        extends ContextualFuncPowerUp[(CounterContext, MapContext[Tank, Int]), Tank]((CounterContext(0), MapContext[Tank, Int]()))(f, g)
        with PowerUpConstraint[Tank](constraint)


object StarPowerUpUtils:

    private val DEFAULT_NUMBER_BULLETS = 1
    private val INCREASED_NUMBER_BULLETS = 2

    val f: ((CounterContext,  MapContext[Tank, Int]), Tank) => Tank =
        case ((counter@CounterContext(0), map: MapContext[Tank, Int]), t: Tank) =>
            map.registerEntity(t, t.tankData.bulletSpeed)
            t.updateTankData(t.tankData.updateBulletSpeed(_ => PowerTankData.supplyData.bulletSpeed))
            counter += 1
            t
        case ((counter, map: MapContext[Tank, Int]), t: Tank) =>
            t.asInstanceOf[TankMultipleShootingBehaviour].shots = INCREASED_NUMBER_BULLETS
            t

    val g: ((CounterContext,  MapContext[Tank, Int]), Tank) => Tank =
        case ((_, map: MapContext[Tank, Int]), t: Tank) =>
            t.updateTankData(t.tankData.updateBulletSpeed(_ => map.getValue(t).get))
            t.asInstanceOf[TankMultipleShootingBehaviour].shots = DEFAULT_NUMBER_BULLETS
            t

    val constraint: (Tank => Boolean) =
        _.isInstanceOf[PlayerTank]








