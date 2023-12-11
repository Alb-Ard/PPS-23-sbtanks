package org.aas.sbtanks.enemies

import org.aas.sbtanks.behaviours.PositionBehaviour
import org.aas.sbtanks.enemies.ai.DirectionUtils.*
import org.aas.sbtanks.enemies.ai.shooting.ShootingEntity
import org.aas.sbtanks.levels.MockLevelFactory
import org.aas.sbtanks.entities.obstacles.LevelObstacle
import org.aas.sbtanks.entities.obstacles.LevelObstacle.PlayerBase
import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.aas.sbtanks.enemies.controller.EnemyTankBuilder
import org.scalatest.matchers.HavePropertyMatcher
import org.aas.sbtanks.entities.obstacles.LevelObstacleType
import org.scalatest.matchers.HavePropertyMatchResult
import org.aas.sbtanks.physics.Collider
import org.aas.sbtanks.entities.obstacles.ObstacleMatchers
import org.aas.sbtanks.physics.PhysicsContainer

class LineOfSightSpec extends AnyFlatSpec
    with Matchers 
    with BeforeAndAfterEach
    with ObstacleMatchers:
        
    def enemyFactory(using PhysicsContainer)(x: Double, y: Double) = EnemyTankBuilder().setPosition(x, y).setSeeThorughBlocks(8).build()

    def toObstacles(colliders: Seq[Collider]) = colliders.filter(_.isInstanceOf[LevelObstacle]).map(_.asInstanceOf[Collider with LevelObstacle])

    "An enemy tank" should "see through line of sight colliders to spot the player base" in:
        given PhysicsContainer = new Object() with PhysicsContainer 

        val tank = MockLevelFactory(enemyFactory)
            .createFromString(
                "UUUUUUU" +
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

    it should "see through line of sight colliders to spot the player base in a negative direction" in:
        given PhysicsContainer = new Object() with PhysicsContainer 

        val tank = MockLevelFactory(enemyFactory)
            .createFromString(
                "UUUUUUU" +
                "U-TBT-U" +
                "U-SwS-U" +
                "U--P--U" +
                "U-WWW-U" +
                "U-WWW-U" +
                "UUUUUUU", 7)
            .getMainEntity[ShootingEntity]

        exactly(1, toObstacles(tank.getCollidersOn(Top))) should have (obstacleType (LevelObstacle.PlayerBase))

        all(toObstacles(tank.getCollidersOn(Bottom))) should not have (obstacleType (LevelObstacle.PlayerBase))
        all(toObstacles(tank.getCollidersOn(Right))) should not have (obstacleType (LevelObstacle.PlayerBase))
        all(toObstacles(tank.getCollidersOn(Left))) should not have (obstacleType (LevelObstacle.PlayerBase))

    it should "be able to spot the enemy base only within a certain range" in:
        given PhysicsContainer = new Object() with PhysicsContainer 

        val tank = MockLevelFactory(enemyFactory)
            .createFromString(
                "U-P-U" +
                "U-W-U" +
                "U-W-U" +
                "U-W-U" +
                "U-B-U", 5)
            .getMainEntity[ShootingEntity]

        all(toObstacles(tank.getCollidersOn(Bottom))) should not have (obstacleType (LevelObstacle.PlayerBase))

















