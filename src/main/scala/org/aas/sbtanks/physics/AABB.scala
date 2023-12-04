package org.aas.sbtanks.physics

/**
  * Represents an axis-aligned bounding box
  *
  * @param x x position
  * @param y y position
  * @param width rect width
  * @param height rect height
  */
case class AABB(x: Double, y: Double, width: Double, height: Double):
    def checkOverlap(other: AABB) = AABB.checkOverlap(this, other)
    
    /**
      * Creates a new box with the same position and dimensions as the original but with normalized size, which means transforming the box
      * position and sizing to be the same in space but with positive dimensions
      * 
      * @return The new normalized AABB box
      */
    def normalized = width match
        case nw if nw < 0 => height match
            case nh if nh < 0 => AABB(x + nw, y + nh, -nw, -nh)
            case _ => AABB(x + nw, y, -nw, height)
        case pw => height match
            case nh if nh < 0 => AABB(x, y + nh, pw, -nh)
            case _ => this
    
    /**
         * Creates a new box with the same position as the original but with its dimensions clamped to a minimum value
         *
         * @param minEdgeSize The minimum size an edge of the new box should be
         * @return The new created AABB box
         */
    def clamped(minEdgeSize: Double) = AABB(
        x,
        y,
        Math.max(minEdgeSize, width),
        Math.max(minEdgeSize, height)
    )

object AABB:
    def checkOverlap(a: AABB, b: AABB) =
        a.x < b.x + b.width 
            && a.x + a.width > b.x
            && a.y < b.y + b.height
            && a.y + a.height > b.y