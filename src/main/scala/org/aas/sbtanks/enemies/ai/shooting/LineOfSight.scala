package org.aas.sbtanks.enemies.ai.shooting

import org.aas.sbtanks.behaviours.{DirectionBehaviour, PositionBehaviour}
import org.aas.sbtanks.enemies.ai.DirectionUtils
import org.aas.sbtanks.enemies.ai.DirectionUtils.*
import org.aas.sbtanks.enemies.ai.shooting.LineOfSight.getCollisionLines
import org.aas.sbtanks.entities.obstacles.LevelObstacle
import org.aas.sbtanks.entities.obstacles.LevelObstacle.PlayerBase
import org.aas.sbtanks.physics.Raycast.*
import org.aas.sbtanks.physics.{Collider, CollisionLayer, PhysicsContainer, PhysicsWorld}





trait LineOfSight(private val lineCollisions: Seq[CollisionLayer], private val exclusion: Seq[Collider], private val seeThrough: Int = 5):
    this: PositionBehaviour with DirectionBehaviour =>

    private val directionIterator = Iterator.continually(Seq(Bottom, Right, Left, Top)).flatten

    def findFirstDirectionCollider(): (Option[Collider], (Double, Double)) =
        val collidersAndDirections = directionIterator
            .map(direction => (getCollidersOn(direction).headOption, direction))
            .find:
                case (collider, _) => collider.isDefined

        collidersAndDirections.getOrElse((None, NoDirection))

    def getCollidersOn(direction: Direction): Seq[Collider] =
        getPriorityColliders(getCollisionLines(this)(direction)())


    private def getPriorityColliders(colliders: Seq[Collider]): Seq[Collider] =
        colliders
            .map(_.asInstanceOf[LevelObstacle with PositionBehaviour])
            .zipWithIndex
            .filter:
                case (collider, index) =>
                    collider.obstacleType == LevelObstacle.PlayerBase && index <= seeThrough
            .map:
                case (collider, _) =>
                    collider.asInstanceOf[Collider]


    private def getVerticalLine(backwards: Boolean = false): Seq[Collider] =
        PhysicsWorld.verticalRayCast(this.positionX, this.positionY, if backwards then Some(-Double.MaxValue) else Option.empty, lineCollisions, exclusion)


    private def getHorizontalLine(backwards: Boolean = false): Seq[Collider] =
        PhysicsWorld.horizontalRayCast(this.positionX, this.positionY, if backwards then Some(-Double.MaxValue) else Option.empty, lineCollisions, exclusion)



object LineOfSight {
    def getCollisionLines(los: LineOfSight): Map[Direction, () => Seq[Collider]] = Map(
        Bottom -> (() => los.getVerticalLine()),
        Top -> (() => los.getVerticalLine(true)),
        Left -> (() => los.getHorizontalLine(true)),
        Right -> (() => los.getHorizontalLine()),
        NoDirection -> (() => Seq.empty)
    )
}




