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

object AABB:
    def checkOverlap(a: AABB, b: AABB) =
        a.x < b.x + b.width 
          && a.x + a.width > b.x
          && a.y < b.y + b.height
          && a.y + a.height > b.y