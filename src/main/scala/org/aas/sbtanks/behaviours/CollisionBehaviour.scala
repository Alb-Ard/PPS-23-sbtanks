package org.aas.sbtanks.behaviours

import org.aas.sbtanks.physics.AABB
import org.aas.sbtanks.physics.PhysicsContainer
import org.aas.sbtanks.physics.Collider
import org.aas.sbtanks.physics.CollisionLayer
import org.aas.sbtanks.event.EventSource

/**
  * A model behaviour that manages collisions with other colliders
  *
  * @param sizeX The collider x size
  * @param sizeY The collider y size
  * @param layer The layer this collider is in
  * @param layerMasks The layers this collider will collide with
  */
trait CollisionBehaviour(using physics: PhysicsContainer)(sizeX: Double, sizeY: Double, override val layer: CollisionLayer, override val layerMasks: Seq[CollisionLayer]) extends Collider:
    this: PositionBehaviour =>
    
    /**
      * Event generated everytime this behaviour overlapped colliders change
      */
    val overlapping = EventSource[Seq[Collider]]

    private var offset = (0D, 0D)

    positionChanged += { _ => physics.refresh() }

    /**
      * Sets an offset from the model's position for the collider bounding box
      *
      * @param offsetX The x offset
      * @param offsetY The y offset
      */
    def setBoundingBoxOffset(offsetX: Double, offsetY: Double): this.type =
        offset = (offsetX, offsetY)
        this

    /**
      * Is this collider overlapping with anything
      *
      * @return true if the collider is overlapping anything, false otherwise
      */
    def overlapsAnything = overlappedColliders.nonEmpty 

    /**
      * The colliders this collider is overlapping
      *
      * @return A sequence of the colliders overlapping this collider
      */
    def overlappedColliders = physics.getOverlaps(this)

    /**
      * @inheritdoc
      */
    override def boundingBox = this match
        case d: DirectionBehaviour if (Math.abs(d.lastValidDirectionX.getOrElse(0D)) > Math.abs(d.lastValidDirectionY.getOrElse(1D))) =>
            AABB(positionX + offset(1), positionY + offset(0), sizeY, sizeX)
        case _ =>
            AABB(positionX + offset(0), positionY + offset(1), sizeX, sizeY)

    /**
     * @inheritdoc
     */
    override def overlapsChanged(overlappingColliders: Seq[Collider]) = 
        overlapping(overlappingColliders)