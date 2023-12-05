package org.aas.sbtanks.behaviours

import org.aas.sbtanks.event.EventSource

trait DamageableBehaviour:
    val destroyed = EventSource[Any]()
    val damaged = EventSource[(Any, Int)]()

    private var damageable = true

    def isDamageable = damageable

    def setDamageable(damageable: Boolean): this.type =
        this.damageable = damageable
        this

    def damage(source: Any, amount: Int): this.type =
        isDamageable match
            case true => 
                val result: this.type = applyDamage(amount)
                damaged(source, amount)
                result
            case _ => this

    protected def applyDamage(amount: Int): this.type

object DamageableBehaviour:
    extension (damageable: DamageableBehaviour)
        def damage(source: Any) =
            damageable.damage(source, 1)