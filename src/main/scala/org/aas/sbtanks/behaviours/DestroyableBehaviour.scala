package org.aas.sbtanks.behaviours

import org.aas.sbtanks.event.EventSource

trait DamageableBehaviour:
    val destroyed = EventSource[Any]()
    val damaged = EventSource[(Any, Int)]()
    val damageableChanged = EventSource[Boolean]()
    private var isDestroyed = false
    private var damageable = true


    def isDamageable = damageable

    def setDamageable(damageable: Boolean): this.type =
        this.damageable = damageable
        damageableChanged(damageable)
        this

    def damage(source: Any, amount: Int): this.type =
        isDamageable match
            case true =>
                damaged(source, amount)
                applyDamage(source, amount)
            case _ => this

    protected def applyDamage(source: Any, amount: Int): this.type

    protected def destroy(source: Any): this.type =
        if isDestroyed then
            return this
        isDestroyed = true
        destroyed(source)
        this



object DamageableBehaviour:
    extension (damageable: DamageableBehaviour)
        def damage(source: Any) =
            damageable.damage(source, 1)