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
import _root_.scalafx.scene.image.Image

import org.aas.sbtanks.player.controller.scalafx.JFXPlayerInputController
import org.aas.sbtanks.player.controller.scalafx.JFXPlayerTankController
import org.aas.sbtanks.player.controller.ControllableTank
import org.aas.sbtanks.entities.tank.view.scalafx.JFXTankView
import org.aas.sbtanks.behaviours.MovementBehaviour
import org.aas.sbtanks.behaviours.SteppedMovementDirectionBehaviour
import org.aas.sbtanks.behaviours.PositionBehaviour
import org.aas.sbtanks.behaviours.CollisionBehaviour
import org.aas.sbtanks.physics.CollisionLayer

class PlayerMovementControlSpec extends AnyFeatureSpec with GivenWhenThen with Matchers with Includes:
    val moveUpInput = KeyEvent(KeyEvent.KEY_PRESSED, "w", "w", W, false, false, false, false)
    val moveDownInput = KeyEvent(KeyEvent.KEY_PRESSED, "s", "s", S, false, false, false, false)
    val moveLeftInput = KeyEvent(KeyEvent.KEY_PRESSED, "a", "a", A, false, false, false, false)
    val moveRightInput = KeyEvent(KeyEvent.KEY_PRESSED, "d", "d", D, false, false, false, false)

    class MockJFXPlayerTankController(tank: ControllableTank) extends JFXPlayerTankController(tank, JFXTankView(null), 1):
        def simulateInput(event: KeyEvent) = inputEvents.handleKeyPressEvent(event)

    info("As a player")
    info("I want to control the movement of my tank")
    info("so that I can navigate through the game map")

    Feature("Player controller") {
        Scenario("The player presses a directional movement input") {
            Given("A tank with a movement behaviour starting at position (0, 0) with a step speed of 1")
            val startingPosition = (0, 0)
            val stepSpeed = 1
            val playerTank = new Object()
                with PositionBehaviour(startingPosition(0), startingPosition(1))
                with MovementBehaviour
                with SteppedMovementDirectionBehaviour(stepSpeed)
                with CollisionBehaviour(1, 1, CollisionLayer.TanksLayer, Seq.empty)
            playerTank.positionX should be (startingPosition(0))
            playerTank.positionY should be (startingPosition(1))

            And("A player controller for that tank")
            val controller = MockJFXPlayerTankController(playerTank)

            And("A game step size of 0.01")
            val timeStep = 0.01

            When("The player presses a movement input")
            controller.simulateInput(moveRightInput)

            And("A game step occours")
            playerTank.step(timeStep)

            Then("The tank should move in the requested direction with respect with delta time")
            (playerTank.positionX - startingPosition(0)) should be (1 * timeStep * stepSpeed)
            (playerTank.positionY - startingPosition(1)) should be (0)
        }
    }