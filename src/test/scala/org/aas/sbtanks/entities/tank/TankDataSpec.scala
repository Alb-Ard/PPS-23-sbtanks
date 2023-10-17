package org.aas.sbtanks.entities.tank

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class TankDataSpec extends AnyFlatSpec with Matchers {
    val fixedTankData: TankData = FixedTankData(10, 20)
    val dynamicTankData: ModifiableTankData = DynamicTankData(10, 20)


    "A tank " should "be created with the correct health and speed" in {
        fixedTankData.health should be (10)
        fixedTankData.speed should be (20)

        dynamicTankData.health should be(10)
        dynamicTankData.speed should be(20)
    }

    it should "be possible to update health and speed" in {

        dynamicTankData.updateHealth(_ + 1)
          .updateSpeed(_ + 1) should be DynamicTankData(11, 21)
    }


    it should "not be possible to update health and speed if the TankData is not modifiable" in {
        """
        fixedTankData.updateHealth(_ + 1)
          .updateSpeed(_ + 1) should be (FixedTankData(10, 20))
        """ shouldNot compile
    }

    "A modifiable tank data" should "always return a modifiable tank data when modified" in {
        dynamicTankData.updateHealth(_ + 1)
          .updateSpeed(_ + 1) should be DynamicTankData(11, 21)
    }

    "A modifiable tank data" should "never return an unmodifiable tank data when modified" in {
        dynamicTankData.updateHealth(_ + 1)
          .updateSpeed(_ + 1) should not be FixedTankData(11, 21)
    }
}
