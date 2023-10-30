package org.aas.sbtanks.entities.tank.factories

import org.aas.sbtanks.entities.tank.structure.Tank
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.language.postfixOps

class TankSpec extends AnyFlatSpec with Matchers:
    import org.aas.sbtanks.entities.tank.structure.Tank.*

    val basicTank = new BasicTank()
    val fastTank = new FastTank()
    val armorTank = new ArmorTank()
    val powerTank = new PowerTank()


    "Enemy tank base stats" should "be configured correctly according to their type" in {
        basicTank.tankData should be (BasicTankData.supplyData)
        fastTank.tankData should be (FastTankData.supplyData)
        armorTank.tankData should be (ArmorTankData.supplyData)
        powerTank.tankData should be (PowerTankData.supplyData)
    }

    val tanks: Seq[Tank] = List(basicTank, fastTank, armorTank, powerTank)

    "Each type of enemy tanks" should "be able to change its data" in {
        tanks.foreach(tank => {
            tank updateTankData (tank.tankData.updateSpeed(_ * 2)
                .updateHealth(_ + 5))
                .updateBulletSpeed(_ + 5)
            

        })
    }





