package org.aas.sbtanks.enemies

import org.aas.sbtanks.behaviours.PositionBehaviour
import org.aas.sbtanks.enemies.ai.DirectionUtils.*
import org.aas.sbtanks.enemies.ai.shooting.ShootingEntity
import org.aas.sbtanks.level.MockLevelFactory
import org.aas.sbtanks.obstacles.LevelObstacle
import org.aas.sbtanks.obstacles.LevelObstacle.PlayerBase
import org.aas.sbtanks.physics.PhysicsWorld
import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class AiShootingSpec extends AnyFlatSpec with Matchers with BeforeAndAfterEach:

    val PLAYER_BASE: LevelObstacle = LevelObstacle(List("obstacles/player_base_1.png"))

    "An enemy tank" should "see through line of sight colliders to spot the player base" in:
        PhysicsWorld.clearColliders()

        val tank = MockLevelFactory()
            .createFromString("UUUUUUU" +
                "U-TTT-U" +
                "U-SwS-U" +
                "U--P--U" +
                "U-WWW-U" +
                "U-WBW-U" +
                "UUUUUUU", 7)
            .getMainEntity
            .asInstanceOf[ShootingEntity]



        tank.getCollidersOn(Bottom) should contain (PLAYER_BASE)

        tank.getCollidersOn(Top) should not contain (PLAYER_BASE)
        tank.getCollidersOn(Right) should not contain (PLAYER_BASE)
        tank.getCollidersOn(Left) should not contain (PLAYER_BASE)


    "An enemy tank" should "be able to spot the enemy base only within a certain range" in:
        PhysicsWorld.clearColliders()

        val tank = MockLevelFactory()
            .createFromString("UUUUU" +
                "U-P-U" +
                "U-W-U" +
                "U-W-U" +
                "U-B-U", 5)
            .getMainEntity
            .asInstanceOf[ShootingEntity]

        tank.getCollidersOn(Bottom) should not contain (PLAYER_BASE)

















