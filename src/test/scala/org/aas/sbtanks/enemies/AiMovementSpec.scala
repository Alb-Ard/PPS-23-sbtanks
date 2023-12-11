package org.aas.sbtanks.enemies

import org.aas.sbtanks.behaviours.PositionBehaviour
import org.aas.sbtanks.enemies.ai.DirectionUtils.*
import org.aas.sbtanks.enemies.ai.fsm.movement.AiMovementStateMachineUtils
import org.aas.sbtanks.enemies.ai.{DirectionUtils, MovementEntity}
import org.aas.sbtanks.entities.tank.controller.TankController.ControllableTank
import org.aas.sbtanks.levels.MockLevelFactory
import org.aas.sbtanks.physics.PhysicsWorld
import org.aas.sbtanks.enemies.controller.AiMovableController
import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.language.postfixOps
import org.aas.sbtanks.enemies.controller.EnemyTankBuilder
import org.aas.sbtanks.physics.PhysicsContainer

class AiMovementSpec extends AnyFlatSpec with Matchers with BeforeAndAfterEach:

    def enemyFactory(using p: PhysicsContainer)(x: Double, y: Double) = EnemyTankBuilder().setPosition(x, y).build()


    "tank " should "go right or left but not down or up" in:
        val physics = new Object() with PhysicsContainer
        given PhysicsContainer = physics



        val tank = MockLevelFactory(enemyFactory)
            .createFromString("UUUUUUU" +
                "U-TTT-U" +
                "U-SwS-U" +
                "U--P--U" +
                "U-WWW-U" +
                "U-WBW-U" +
                "UUUUUUU", 7)
            .getMainEntity
            .asInstanceOf[MovementEntity]

        tank.testMoveRelative(1.0, 0.0) should be (true)
        tank.testMoveRelative(-1.0, 0.0) should be (true)
        tank.testMoveRelative(0.0, 1.0) should be (false)
        tank.testMoveRelative(0.0, -1.0) should be (false)

        tank.moveRelative(2.0, 0.0)
            .asInstanceOf[MovementEntity]
            .testMoveRelative(0.0, 1.0) should be (true)


    "Tank ai movement" should "prioritize bottom directed movement when possible" in:
        val physics = new Object() with PhysicsContainer
        given PhysicsContainer = physics

        val tank: MovementEntity = MockLevelFactory(enemyFactory)
            .createFromString(
                "UUUUU" +
                "UU-UU" +
                "U-P-U" +
                "UU-UU" +
                "UUUUU", 5
            )
            .getMainEntity
            .asInstanceOf[MovementEntity]




        val (newDir, newState) = AiMovementStateMachineUtils.computeAiState(tank)


        newDir should be (tank.directionX, tank.directionX + 1.0)


    "Tank ai movement" should "should prioritize right or left directions random only when bottom direction is not possible" in:
        val physics = new Object() with PhysicsContainer
        given PhysicsContainer = physics

        val tank: MovementEntity = MockLevelFactory(enemyFactory)
            .createFromString(
                "UUUUU" +
                    "UU-UU" +
                    "U-P-U" +
                    "UUUUU" +
                    "UUUUU", 5
            )
            .getMainEntity
            .asInstanceOf[MovementEntity]



        var (newTopDir, topState) = AiMovementStateMachineUtils.computeAiState(tank, movementBias = 1)


        newTopDir should  (be(Right) or be(Left))


    "Tank ai movement" should "should only have a possibility to move top if bottom direction and right/left is not available" in :
        val physics = new Object() with PhysicsContainer

        given PhysicsContainer = physics

        val tank: MovementEntity = MockLevelFactory(enemyFactory)
            .createFromString(
                "UUUUU" +
                    "UUUUU" +
                    "U---U" +
                    "UUPUU" +
                    "UUUUU", 5
            )
            .getMainEntity
            .asInstanceOf[MovementEntity]


        var (newTopDir, topState) = AiMovementStateMachineUtils.computeAiState(tank, movementBias = 1, maxIteration = 20)


        newTopDir should be(Top)















