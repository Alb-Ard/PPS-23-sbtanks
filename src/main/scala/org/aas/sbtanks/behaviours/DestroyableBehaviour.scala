package org.aas.sbtanks.behaviours

import org.aas.sbtanks.event.EventSource

trait DamageableBehaviour:
    val destroyed = EventSource[Unit]()
    val damaged = EventSource[Int]()

    private var damageable = true

    def isDamageable = damageable

    def setDamageable(damageable: Boolean): this.type =
        this.damageable = damageable
        this

    def damage(amount: Int): this.type =
        isDamageable match
            case true => 
                val result: this.type = applyDamage(amount)
                damaged(amount)
                result
            case _ => this

    protected def applyDamage(amount: Int): this.type

object DamageableBehaviour:
    extension (damageable: DamageableBehaviour)
        def damage() =
            damageable.damage(1)