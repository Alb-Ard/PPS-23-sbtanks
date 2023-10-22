package org.aas.sbtanks.entities.bullet

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class BulletDataSpec extends AnyFlatSpec with Matchers {

    val doubleSpeedBullet: BulletData & BulletSpeedUpdater = new BulletData(20) with BulletSpeedUpdater
    val fixedSpeedBullet: BulletData = BulletData(20)

    "a bullet" should "be created with proper speed" in {
        fixedSpeedBullet.speed should be (20)
    }

    it should "be possible to double speed of bullet" in {
        doubleSpeedBullet.doubleSpeed() should be (new BulletData(20 * 2) with BulletSpeedUpdater)
    }


    it should "not be possible to update health and speed if the TankData is not modifiable" in {
        """
        fixedBulletSpeed.doubleSpeed() should be (BulletData(40))
        """ shouldNot compile
    }


    "As modifiable bullet speed data" should "never return an unmodifiable speed bullet data when modified" in {  
        doubleSpeedBullet.doubleSpeed() shouldBe a[BulletSpeedUpdater] 
    }
}
