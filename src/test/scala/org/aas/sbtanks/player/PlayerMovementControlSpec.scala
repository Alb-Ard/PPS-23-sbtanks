package org.aas.sbtanks.player

import org.scalatest.featurespec.AnyFeatureSpec
import org.scalatest.GivenWhenThen
import org.scalatest.matchers.should.Matchers

import javafx.scene.input.KeyEvent

import _root_.scalafx.Includes
import _root_.scalafx.scene.input.KeyCode.W
import _root_.scalafx.scene.input.KeyCode.S
import _root_.scalafx.scene.input.KeyCode.A
import _root_.scalafx.scene.input.KeyCode.D

import org.aas.sbtanks.player.scalafx.JFXPlayerInputController
import org.aas.sbtanks.behaviours.MovementBehaviour
import org.aas.sbtanks.behaviours.SteppedMovementDirectionBehaviour

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

            And("A player controlled tank with a movement behaviour starting at position (0, 0) with a step speed of 1")
            val startingPosition = (0, 0)
            val stepSpeed = 1
            val playerTank = new Object() with MovementBehaviour(startingPosition(0), startingPosition(1)) with SteppedMovementDirectionBehaviour(stepSpeed)
            playerTank.positionX should be (startingPosition(0))
            playerTank.positionY should be (startingPosition(1))
            controller.moved += playerTank.moveTowards
            playerTank.stepMoved += playerTank.moveRelative

            And("A game step size of 0.01")
            val timeStep = 0.01

            When("The player presses a movement input")
            controller.handleKeyPressEvent(moveRightInput)

            And("A game step occours")
            playerTank.step(timeStep)

            Then("The tank should move in the requested direction with respect with delta time")
            (playerTank.positionX - startingPosition(0)) should be (1 * timeStep * stepSpeed)
            (playerTank.positionY - startingPosition(1)) should be (0)
        }
    }