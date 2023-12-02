package org.aas.sbtanks.entities.obstacles

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.aas.sbtanks.behaviours.PositionMatchers

class ObstacleSpec extends AnyFlatSpec with Matchers with ObstacleMatchers with PositionMatchers:
    "A obstacle factory" should "create an obstacle of the correct type" in {
        all(LevelObstacle.BrickWall(0, 0)) should have (obstacleType (LevelObstacle.BrickWall))
    }

    it should "create an obstacle at the given position" in {
        all(LevelObstacle.SteelWall(2, 3)) should have (position (2, 3))
    }

    "A positionable obstacle" should "be able to find near obstacles" in {
        val o = LevelObstacle.PlayerBase(2, 2)(0)
        val near = Seq(
            LevelObstacle.SteelWall(1, 2)(0),
            LevelObstacle.SteelWall(3, 3)(0),
        )
        val nonNear = Seq(
            LevelObstacle.SteelWall(0, 0)(0),
            LevelObstacle.SteelWall(4, 2)(0),
        )
        o.getNearObstacles(() => near.union(nonNear)) should (contain theSameElementsAs (near) and not contain theSameElementsAs (nonNear))
    }