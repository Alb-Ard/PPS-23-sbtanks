package org.aas.sbtanks.enemies.ai.shooting

import org.aas.sbtanks.behaviours.{DirectionBehaviour, PositionBehaviour}
import org.aas.sbtanks.enemies.ai.DirectionUtils
import org.aas.sbtanks.enemies.ai.DirectionUtils.*
import org.aas.sbtanks.enemies.ai.shooting.LineOfSight.getCollisionLines
import org.aas.sbtanks.entities.obstacles.LevelObstacle
import org.aas.sbtanks.entities.obstacles.LevelObstacle.PlayerBase
import org.aas.sbtanks.physics.Raycast.*
import org.aas.sbtanks.physics.{Collider, CollisionLayer, PhysicsContainer}
import org.aas.sbtanks.physics.Raycast

/**
 * Represents an entity's cardinal line of sight behavior searching for a specific target, taking into accounts the actual physic of the world
 *
 * @param physics         The container for physics-related functionality.
 * @param lineCollisions  The sequence of collision layers checked for the line of sight.
 * @param exclusion       The sequence of colliders to be excluded from line of sight calculations.
 * @param seeThrough      The maximum see-through distance. Default is 8.
 * @param lineOffset      The offset value for improvement in the line of sight calculations.
 */
trait LineOfSight(using physics: PhysicsContainer)(private val targetPredicate: Collider => Boolean)(private val lineCollisions: Seq[CollisionLayer], private val exclusion: Seq[Collider], private val seeThrough: Int = 8, private val lineOffset: Double):
    this: PositionBehaviour with DirectionBehaviour =>

    /**
     * Infinite iterator providing directions for line of sight calculations.
     */
    private val directionIterator = Iterator.continually(Seq(Bottom, Right, Left, Top)).flatten


    /**
     * Finds the first collider in the line of sight in any direction.
     *
     * @return A tuple containing an optional collider and the corresponding direction or an empty collider with Nodirection if no target collider is found
     */
    def findFirstDirectionCollider(): (Option[Collider], (Double, Double)) =
        val collidersAndDirections = directionIterator
            .map(direction => (getCollidersOn(direction).headOption, direction))
            .take(getCollisionLines(this).size)
            .find:
                case (collider, _) => collider.isDefined

        collidersAndDirections.getOrElse((None, NoDirection))

    /**
     * Retrieves colliders in a specific direction based on direction to seach on.
     *
     * @param direction The direction for which colliders are retrieved.
     * @return A sequence of colliders in the specified direction.
     */
    def getCollidersOn(direction: Direction): Seq[Collider] =
        getPriorityColliders(getCollisionLines(this)(direction)())

    /**
     * Retrieves colliders with priority based on the targetPredicate condition.
     *
     * @param colliders The sequence of colliders to be prioritized.
     * @return A sequence of prioritized colliders.
     */
    private def getPriorityColliders(colliders: Seq[Collider]): Seq[Collider] =
        colliders
            .zipWithIndex
            .filter:
                case (collider, index) =>
                    targetPredicate(collider) && index <= seeThrough
            .map:
                case (collider, _) => collider


    /**
     * Retrieves a vertical line of colliders in the line of sight.
     *
     * @param backwards If true, retrieves a backward-facing line.
     * @return A sequence of colliders in the vertical line.
     */
    private def getVerticalLine(backwards: Boolean = false): Seq[Collider] =
        physics.verticalRayCast(this.positionX + lineOffset, this.positionY + lineOffset, if backwards then Option(Raycast.INFINITE_NEGATIVE) else Option.empty, lineCollisions, exclusion)

    /**
     * Retrieves a horizontal line of colliders in the line of sight.
     *
     * @param backwards If true, retrieves a backward-facing line.
     * @return A sequence of colliders in the horizontal line.
     */
    private def getHorizontalLine(backwards: Boolean = false): Seq[Collider] =
        physics.horizontalRayCast(this.positionX, this.positionY, if backwards then Option(Raycast.INFINITE_NEGATIVE) else Option.empty, lineCollisions, exclusion)



object LineOfSight:

    /**
     * Retrieves a mapping of directions to functions providing collision lines.
     *
     * @param los The LineOfSight instance for which collision lines are generated.
     * @return A mapping of directions to functions providing collision lines.
     */
    def getCollisionLines(los: LineOfSight): Map[Direction, () => Seq[Collider]] = Map(
        Bottom -> (() => los.getVerticalLine()),
        Top -> (() => los.getVerticalLine(true)),
        Left -> (() => los.getHorizontalLine(true)),
        Right -> (() => los.getHorizontalLine()),
        NoDirection -> (() => Seq.empty)
    )




