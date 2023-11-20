package org.aas.sbtanks.enemies.ai.shooting

import org.aas.sbtanks.behaviours.{DirectionBehaviour, PositionBehaviour}
import org.aas.sbtanks.enemies.ai.DirectionUtils.*
import org.aas.sbtanks.enemies.ai.shooting.LineOfSight.getCollisionLines
import org.aas.sbtanks.obstacles.LevelObstacle
import org.aas.sbtanks.obstacles.LevelObstacle.PlayerBase
import org.aas.sbtanks.physics.Raycast.*
import org.aas.sbtanks.physics.{Collider, CollisionLayer, PhysicsContainer, PhysicsWorld}

trait LineOfSight(private val lineCollisions: Seq[CollisionLayer], private val exclusion: Seq[Collider], private val seeThrough: Int = 5):
    this: PositionBehaviour with DirectionBehaviour =>


    def getCollidersOn(direction: Direction): Seq[Collider] =
        getPriorityColliders(getCollisionLines(this)(direction))

    private def getPriorityColliders(colliders: Seq[Collider]): Seq[Collider] =
        colliders
            .map(_.asInstanceOf[LevelObstacle with PositionBehaviour])
            .zipWithIndex
            .filter:
                case (collider, index) =>
                    PlayerBase(collider.positionX, collider.positionY).contains(collider) && index <= seeThrough
            .map:
                case (collider, _) =>
                    collider.asInstanceOf[Collider]


    private def getVerticalLine(backwards: Boolean = false): Seq[Collider] =
        PhysicsWorld.verticalRayCast(this.positionX, this.positionY, if backwards then Some(-Int.MaxValue) else Option.empty, lineCollisions, exclusion)


    private def getHorizontalLine(backwards: Boolean = false): Seq[Collider] =
        PhysicsWorld.horizontalRayCast(this.positionX, this.positionY, if backwards then Some(-Int.MaxValue) else Option.empty, lineCollisions, exclusion)


object LineOfSight {
    def getCollisionLines(los: LineOfSight): Map[Direction, Seq[Collider]] = Map(
        Bottom -> los.getVerticalLine(),
        Top -> los.getVerticalLine(true),
        Left -> los.getHorizontalLine(true),
        Right -> los.getHorizontalLine()
    )
}



