package org.aas.sbtanks.entities.tank

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class TankDataSpec extends AnyFlatSpec with Matchers {


    val tankData: TankData & TankDataUpdater = new TankData(10, 20, 30) with TankDataUpdater
    val fixedTankData: TankData = TankData(11, 21, 31)



    "A tank " should "be created with the correct health and speed" in {
        tankData.health should be (10)
        tankData.speed should be (20)

        tankData.health should be(10)
        tankData.speed should be(20)
    }

    it should "be possible to update health and speed" in {

        tankData.updateHealth(_ + 1)
          .updateSpeed(_ + 1)
          .updateBulletSpeed(_ + 1) should be (new TankData(10 + 1, 20 + 1, 30 + 1) with TankDataUpdater)
    }


    it should "not be possible to update health and speed if the TankData is not modifiable" in {
        """
        fixedTankData.updateHealth(_ + 1)
          .updateSpeed(_ + 1)
          .updateBulletSpeed(_ + 1) should be (TankData(12, 22, 33))
        """ shouldNot compile
    }


    "As modifiable tank data" should "never return an unmodifiable tank data when modified" in {
        tankData.updateHealth(_ + 1)
          .updateSpeed(_ + 1)
          .updateBulletSpeed(_ + 1) shouldBe a[TankDataUpdater]
    }





}
