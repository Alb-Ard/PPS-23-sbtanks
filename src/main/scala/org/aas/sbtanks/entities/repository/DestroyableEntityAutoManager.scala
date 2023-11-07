package org.aas.sbtanks.entities.repository

import org.aas.sbtanks.entities.repository.EntityMvRepositoryContainer
import org.aas.sbtanks.behaviours.DamageableBehaviour

/**
  * Automatically removes models (and views) when a damageable model is destroyed from an entity repository
  */
trait DestroyableEntityAutoManager[M, V]:
    this: EntityMvRepositoryContainer[M, V] =>

    modelViewAdded += { (m, _) => m match
        case d: DamageableBehaviour => bindDestroy(d)
        case _ => ()
    }

    def bindDestroy(destroyable: M with DamageableBehaviour) =
        destroyable.destroyed += { _ => removeModelView(destroyable) }