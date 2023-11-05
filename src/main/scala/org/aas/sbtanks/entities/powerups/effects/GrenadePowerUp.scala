package org.aas.sbtanks.entities.powerups.effects

import org.aas.sbtanks.entities.powerups.PowerUp.PowerUpConstraint
import org.aas.sbtanks.entities.tank.structure.Tank

object Grenade:
    import org.aas.sbtanks.entities.powerups.PowerUp.FuncPowerUp
    import GrenadePowerUpUtils.*

    case object GrenadePowerUp extends FuncPowerUp[Tank](f, g) with PowerUpConstraint[Tank](constraint)

object GrenadePowerUpUtils:
    val f: Tank => Tank = t => {t updateTankData(t.tankData.updateHealth(_ => 0)); t}

    val g: Tank => Tank = identity


    /*
        TODO: constraint => should be and enemy not in spawning state
     */
    val constraint: Tank => Boolean = _ => true
