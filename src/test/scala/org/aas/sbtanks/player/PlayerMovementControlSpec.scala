package org.aas.sbtanks.player

import org.scalatest.featurespec.AnyFeatureSpec
import org.scalatest.GivenWhenThen
import org.aas.sbtanks.entities.tank.behaviours.TankMovementBehaviour
import org.scalatest.matchers.should.Matchers

class PlayerMovementControlSpec extends AnyFeatureSpec with GivenWhenThen with Matchers:
    class MockPlayerInputController extends PlayerInputEvents:
        def simulateMoveInput(amountX: Int, amountY: Int) = invokeListeners(movementInputListeners) { listener => listener(amountX, amountY) }

    info("As a player")
    info("I want to control the movement of my tank")
    info("so that I can navigate through the game map")

    Feature("Player controller") {
        Scenario("The player presses a directional movement input") {
            Given("A player controller")
            val controller = MockPlayerInputController()

            And("A player controlled tank with a movement behaviour starting at position (0, 0)")
            val startingPosition = (0, 0)
            val playerTank = new Object() with TankMovementBehaviour(startingPosition(0), startingPosition(1))
            playerTank.positionX should be (startingPosition(0))
            playerTank.positionY should be (startingPosition(1))
            controller.movementInputListeners.addOne(playerTank.move)

            When("The player presses a movement input")
            controller.simulateMoveInput(1, 0)

            Then("The tank should move in the requested direction")
            (playerTank.positionX - startingPosition(0)) should be (1)
            (playerTank.positionY - startingPosition(1)) should be (0)
        }
    }