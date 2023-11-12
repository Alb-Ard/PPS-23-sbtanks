package org.aas.sbtanks.enemies.ai.shooting

import org.aas.sbtanks.behaviours.{DirectionBehaviour, PositionBehaviour}
import org.aas.sbtanks.physics.Raycast.*
import org.aas.sbtanks.physics.{Collider, CollisionLayer, PhysicsContainer, PhysicsWorld}

trait LineOfSight(private val priorityCollider: Seq[Collider], private val priorityTake: Int, private val lineCollisions: Seq[CollisionLayer], private val exclusion: Seq[Collider]):
    this: PositionBehaviour with DirectionBehaviour =>


    /*
        TODO: check with the current direction, otherwise change it to the one with the major amount of priority colliders
     */

    def getPriorityCollisionLine: (Double, Double) =
        if getPriorityColliders(getVerticalLine).size >= getPriorityColliders(getHorizzontalLine).size then (0,1) else (1,0)




    private def getPriorityColliders(colliders: Seq[Collider]): Seq[Collider] = colliders.take(priorityTake).filter(priorityCollider.contains(_))

    private def getVerticalLine: Seq[Collider] =
        PhysicsWorld.verticalRayCast(this.positionX, this.positionY, Option.empty, lineCollisions, exclusion)

    private def getHorizzontalLine: Seq[Collider] =
        PhysicsWorld.horizontalRayCast(this.positionX, this.positionY, Option.empty, lineCollisions, exclusion)






