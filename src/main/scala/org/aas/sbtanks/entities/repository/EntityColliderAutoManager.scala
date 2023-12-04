package org.aas.sbtanks.entities.repository

import org.aas.sbtanks.physics.Collider
import org.aas.sbtanks.physics.PhysicsWorld
import org.aas.sbtanks.physics.AABB
import org.aas.sbtanks.entities.repository.context.EntityRepositoryContext

trait EntityColliderAutoManager[M, V]:
    this: EntityMvRepositoryContainer[M, V] =>
    
    modelViewAdded += { (m, _) => registerEntity(m) }
    modelViewRemoved += { (m, _) => unregisterEntity(m) }

    private def registerEntity(entityModel: M): this.type =
        entityModel match
            case c: Collider => PhysicsWorld.registerCollider(c)
            case _ => ()
        this
    
    private def unregisterEntity(entityModel: M): this.type =
        entityModel match
            case c: Collider => PhysicsWorld.unregisterCollider(c)
            case _ => ()
        this

trait EntityColliderDebugger[M, VSlotKey, VSlot](using context: EntityRepositoryContext[?, VSlotKey, VSlot])(active: Boolean, gameSlot: VSlotKey):
    this: EntityMvRepositoryContainer[M, ?] =>

    if active then
        modelViewAdded += { (m, _) => m match
            case c: Collider => context.viewSlots.get(gameSlot).foreach(addDebugView(c, _))
            case _ => ()
        }
        modelViewRemoved += { (m, _) => m match
            case c: Collider => context.viewSlots.get(gameSlot).foreach(removeDebugView(c, _)) 
            case _ => ()
        }

    protected def addDebugView(collider: Collider, container: VSlot): this.type
    protected def removeDebugView(collider: Collider, container: VSlot): this.type