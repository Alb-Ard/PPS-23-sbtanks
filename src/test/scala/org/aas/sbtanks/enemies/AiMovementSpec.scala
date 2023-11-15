package org.aas.sbtanks.enemies

import org.aas.sbtanks.behaviours.PositionBehaviour
import org.aas.sbtanks.enemies.ai.DirectionUtils.*
import org.aas.sbtanks.enemies.ai.fsm.movement.AiMovementStateMachineUtils
import org.aas.sbtanks.enemies.ai.{DirectionUtils, MovementEntity}
import org.aas.sbtanks.entities.tank.controller.TankController.ControllableTank
import org.aas.sbtanks.level.MockLevelFactory
import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.language.postfixOps

class AiMovementSpec extends AnyFlatSpec with Matchers with BeforeAndAfterEach:



    "tank " should "go right or left but not down or up" in:
        val tank = MockLevelFactory()
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
        val tank: MovementEntity = MockLevelFactory()
            .createFromString(
                "UUUUU" +
                "UU-UU" +
                "U-P-U" +
                "UU-UU" +
                "UUUUU", 5
            )
            .getMainEntity
            .asInstanceOf[MovementEntity]



        val (newPos, newState) = AiMovementStateMachineUtils.computeAiState(tank)


        newPos should be (tank.positionX, tank.positionY + 1.0)

    "Tank ai movement" should "run across top directions only if no other options is available while right and left directions choices are random" in:
        val tank: MovementEntity = MockLevelFactory()
            .createFromString(
                "UUUUU" +
                    "UU-UU" +
                    "U---U" +
                    "UUPUU" +
                    "UUUUU", 5
            )
            .getMainEntity
            .asInstanceOf[MovementEntity]



        val (newTopPos, topState) = AiMovementStateMachineUtils.computeAiState(tank)

        newTopPos should be (tank.positionX, tank.positionY - 1)




        val (newRightOrLeftPos, rightOrLeftState) = AiMovementStateMachineUtils
            .computeAiState(topState.setPosition(newTopPos._1, newTopPos._2).asInstanceOf[MovementEntity])


        newRightOrLeftPos should (be(topState.positionX - 1, topState.positionY) or be(topState.positionX + 1, topState.positionY))

















