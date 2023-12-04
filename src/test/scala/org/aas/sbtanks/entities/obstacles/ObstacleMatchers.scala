package org.aas.sbtanks.entities.obstacles

import org.aas.sbtanks.behaviours.PositionBehaviour
import org.aas.sbtanks.enemies.ai.DirectionUtils.*
import org.aas.sbtanks.enemies.ai.shooting.ShootingEntity
import org.aas.sbtanks.entities.obstacles.LevelObstacle
import org.aas.sbtanks.entities.obstacles.LevelObstacle.PlayerBase
import org.aas.sbtanks.physics.PhysicsContainer
import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.aas.sbtanks.enemies.controller.EnemyTankBuilder
import org.scalatest.matchers.HavePropertyMatcher
import org.aas.sbtanks.entities.obstacles.LevelObstacleType
import org.scalatest.matchers.HavePropertyMatchResult
import org.aas.sbtanks.physics.Collider

trait ObstacleMatchers:
    def obstacleType(expectedValue: LevelObstacleType) =
        new HavePropertyMatcher[LevelObstacle, LevelObstacleType]:
            def apply(obstacle: LevelObstacle) =
                HavePropertyMatchResult(
                    obstacle.obstacleType == expectedValue,
                    "obstacleType",
                    expectedValue,
                    obstacle.obstacleType
                )