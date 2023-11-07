package org.aas.sbtanks.entities.repository

import org.aas.sbtanks.physics.Collider
import org.aas.sbtanks.physics.PhysicsWorld

trait EntityColliderAutoManager[M, V]:
    this: EntityMvRepositoryContainer[M, V] =>
    
    modelViewAdded += { (m, _) => registerEntity(m) }
    modelViewRemoved += { (m, _) => unregisterEntity(m) }

    private def registerEntity(entityModel: M): this.type =
        Option(entityModel.asInstanceOf[Collider]).foreach(PhysicsWorld.registerCollider)
        this
    
    private def unregisterEntity(entityModel: M): this.type =
        Option(entityModel.asInstanceOf[Collider]).foreach(PhysicsWorld.unregisterCollider)
        this