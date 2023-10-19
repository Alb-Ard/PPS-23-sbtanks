package org.aas.sbtanks.entities.powerups

import org.aas.sbtanks.entities.tank.structure.Tank
import org.aas.sbtanks.entities.tank.structure.Tank.BasicTank





trait PowerUp[E](f: E => E):
    def apply(entity: E): Option[E] = Some(f(entity))



trait PowerUpConstraint[E](predicate: E => Boolean) extends PowerUp[E]:
    override def apply(entity: E): Option[E] = Some(entity).filter(predicate).flatMap(super.apply)




object PowerUp extends App:

    val tank = new BasicTank()

    println(tank.tankData.health)

    val healthPowerUp = new PowerUp[Tank](tank => {
        tank updateTankData(tank.tankData.updateHealth(_ + 10));
        tank
    })
        with PowerUpConstraint[Tank](_.tankData.health < 2)









