package org.aas.sbtanks.behaviours

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class DamageableSpec extends AnyFlatSpec with Matchers:
    "A damageable behaviour" should "apply damage by default" in {
        var wasDamaged = false
        val damageable = new Object() with DamageableBehaviour:
            override protected def applyDamage(amount: Int) =
                wasDamaged = true
                this
        damageable.damage()
        wasDamaged should be (true)
    }
    
    "A damageable behaviour" should "apply damage when it is damageable" in {
        var wasDamaged = false
        val damageable = new Object() with DamageableBehaviour:
            override protected def applyDamage(amount: Int) =
                wasDamaged = true
                this
        damageable.setDamageable(true).damage()
        wasDamaged should be (true)
    }
    
    "A damageable behaviour" should "not apply damage when it is not damageable" in {
        var wasDamaged = false
        val damageable = new Object() with DamageableBehaviour:
            override protected def applyDamage(amount: Int) =
                wasDamaged = true
                this
        damageable.setDamageable(false).damage()
        wasDamaged should be (false)
    }

    "A damageable behaviour" should "apply the correct amount of damage" in {
        var appliedDamage = Option.empty[Int]
        val damageable = new Object() with DamageableBehaviour:
            override protected def applyDamage(amount: Int) =
                appliedDamage = Option(amount)
                this
        damageable.setDamageable(true).damage(2)
        appliedDamage should be (Some(2))
    }
