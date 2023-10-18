package org.aas.sbtanks.entities.bullet

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class BulletDataSpec extends AnyFlatSpec with Matchers {
    val fixedBulletData: BulletData = FixedBulletData(20)
    val dynamicBulletData: BulletData = DynamicBulletData(20)

    "a bullet" should "be created with proper speed" in {
      fixedBulletData.speed should be (20)

      dynamicBulletData.speed should be (20)
    }

    it should "be possible to update health and speed" in {

      dynamicBulletData.updateSpeed(_ + 1) should be(DynamicBulletData(11, 21))
    }


    it should "not be possible to update health and speed if the TankData is not modifiable" in {
      """
              fixedBulletData.updateSpeed(_ + 1) should be (FixedBulletData(10, 20))
              """ shouldNot compile
    }


    "As modifiable tank data" should "never return an unmodifiable tank data when modified" in {
      dynamicBulletData.updateSpeed(_ + 1) shouldBe a[ModifiableBulletData]
    }
}
