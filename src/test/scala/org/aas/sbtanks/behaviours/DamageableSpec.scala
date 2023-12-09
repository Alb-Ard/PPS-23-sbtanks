package org.aas.sbtanks.behaviours

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import org.aas.sbtanks.behaviours.DamageableBehaviour._

class DamageableSpec extends AnyFlatSpec with Matchers:
    "A damageable behaviour" should "apply damage by default" in {
        var wasDamaged = false
        val damageable = new Object() with DamageableBehaviour:
            override protected def applyDamage(source: Any, amount: Int) =
                wasDamaged = true
                this
        damageable.damage(())
        wasDamaged should be (true)
    }

    it should "invoke the damaged event when damaged" in {
        var wasDamaged = false
        val damageable = new Object() with DamageableBehaviour:
            override protected def applyDamage(source: Any, amount: Int) =
                this
        damageable.damaged += { _ => wasDamaged = true }
        damageable.damage(())
        wasDamaged should be (true)
    }
    
    it should "apply damage when it is damageable" in {
        var wasDamaged = false
        val damageable = new Object() with DamageableBehaviour:
            override protected def applyDamage(source: Any, amount: Int) =
                wasDamaged = true
                this
        damageable.setDamageable(true).damage(())
        wasDamaged should be (true)
    }
    
    it should "not apply damage when it is not damageable" in {
        var wasDamaged = false
        val damageable = new Object() with DamageableBehaviour:
            override protected def applyDamage(source: Any, amount: Int) =
                wasDamaged = true
                this
        damageable.setDamageable(false).damage(())
        wasDamaged should be (false)
    }

    it should "apply the correct amount of damage" in {
        var appliedDamage = Option.empty[Int]
        val damageable = new Object() with DamageableBehaviour:
            override protected def applyDamage(source: Any, amount: Int) =
                appliedDamage = Option(amount)
                this
        damageable.setDamageable(true).damage(2)
        appliedDamage should be (Some(2))
    }
