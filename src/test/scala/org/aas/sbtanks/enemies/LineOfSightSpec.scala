package org.aas.sbtanks.enemies

import org.aas.sbtanks.behaviours.PositionBehaviour
import org.aas.sbtanks.enemies.ai.DirectionUtils.*
import org.aas.sbtanks.enemies.ai.shooting.ShootingEntity
import org.aas.sbtanks.level.MockLevelFactory
import org.aas.sbtanks.entities.obstacles.LevelObstacle
import org.aas.sbtanks.entities.obstacles.LevelObstacle.PlayerBase
import org.aas.sbtanks.physics.PhysicsWorld
import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.aas.sbtanks.enemies.controller.EnemyTankBuilder
import org.scalatest.matchers.HavePropertyMatcher
import org.aas.sbtanks.entities.obstacles.LevelObstacleType
import org.scalatest.matchers.HavePropertyMatchResult
import org.aas.sbtanks.physics.Collider
import org.aas.sbtanks.entities.obstacles.ObstacleMatchers

class LineOfSightSpec extends AnyFlatSpec
    with Matchers 
    with BeforeAndAfterEach
    with ObstacleMatchers:
        
    def enemyFactory(x: Double, y: Double) = EnemyTankBuilder().setPosition(x, y).build()

    def toObstacles(colliders: Seq[Collider]) = colliders.filter(_.isInstanceOf[LevelObstacle]).map(_.asInstanceOf[Collider with LevelObstacle])

    "An enemy tank" should "see through line of sight colliders to spot the player base" in:
        PhysicsWorld.clearColliders()

        val tank = MockLevelFactory(enemyFactory)
            .createFromString("UUUUUUU" +
                "U-TTT-U" +
                "U-SwS-U" +
                "U--P--U" +
                "U-WWW-U" +
                "U-WBW-U" +
                "UUUUUUU", 7)
            .getMainEntity[ShootingEntity]

        exactly(1, toObstacles(tank.getCollidersOn(Bottom))) should have (obstacleType (LevelObstacle.PlayerBase))

        all(toObstacles(tank.getCollidersOn(Top))) should not have (obstacleType (LevelObstacle.PlayerBase))
        all(toObstacles(tank.getCollidersOn(Right))) should not have (obstacleType (LevelObstacle.PlayerBase))
        all(toObstacles(tank.getCollidersOn(Left))) should not have (obstacleType (LevelObstacle.PlayerBase))


    "An enemy tank" should "be able to spot the enemy base only within a certain range" in:
        PhysicsWorld.clearColliders()

        val tank = MockLevelFactory(enemyFactory)
            .createFromString("UUUUU" +
                "U-P-U" +
                "U-W-U" +
                "U-W-U" +
                "U-B-U", 5)
            .getMainEntity[ShootingEntity]

        all(toObstacles(tank.getCollidersOn(Bottom))) should not have (obstacleType (LevelObstacle.PlayerBase))

















