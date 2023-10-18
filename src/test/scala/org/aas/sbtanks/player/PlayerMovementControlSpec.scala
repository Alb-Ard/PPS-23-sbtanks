package org.aas.sbtanks.player

import org.scalatest.featurespec.AnyFeatureSpec
import org.scalatest.GivenWhenThen
import org.aas.sbtanks.behaviours.TankMovementBehaviour
import org.scalatest.matchers.should.Matchers
import org.aas.sbtanks.player.scalafx.JFXPlayerInputController
import javafx.scene.input.KeyEvent
import _root_.scalafx.scene.input.KeyCode.W
import _root_.scalafx.Includes
import _root_.scalafx.scene.input.KeyCode.S
import _root_.scalafx.scene.input.KeyCode.A
import _root_.scalafx.scene.input.KeyCode.D

class PlayerMovementControlSpec extends AnyFeatureSpec with GivenWhenThen with Matchers with Includes:
    val moveUpInput = KeyEvent(KeyEvent.KEY_PRESSED, "w", "w", W, false, false, false, false)
    val moveDownInput = KeyEvent(KeyEvent.KEY_PRESSED, "s", "s", S, false, false, false, false)
    val moveLeftInput = KeyEvent(KeyEvent.KEY_PRESSED, "a", "a", A, false, false, false, false)
    val moveRightInput = KeyEvent(KeyEvent.KEY_PRESSED, "d", "d", D, false, false, false, false)

    info("As a player")
    info("I want to control the movement of my tank")
    info("so that I can navigate through the game map")

    Feature("Player controller") {
        Scenario("The player presses a directional movement input") {
            Given("A player controller")
            val controller = JFXPlayerInputController()

            And("A player controlled tank with a movement behaviour starting at position (0, 0)")
            val startingPosition = (0, 0)
            val playerTank = new Object() with TankMovementBehaviour(startingPosition(0), startingPosition(1))
            playerTank.positionX should be (startingPosition(0))
            playerTank.positionY should be (startingPosition(1))
            controller.movementInputListeners.addOne(playerTank.move)

            When("The player presses a movement input")
            controller.handleKeyPressEvent(moveRightInput)

            Then("The tank should move in the requested direction")
            (playerTank.positionX - startingPosition(0)) should be (1)
            (playerTank.positionY - startingPosition(1)) should be (0)
        }
    }