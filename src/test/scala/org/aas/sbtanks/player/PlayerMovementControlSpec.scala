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

import org.aas.sbtanks.entities.tank.controller.TankController.ControllableTank
import org.aas.sbtanks.entities.tank.view.scalafx.JFXTankView
import org.aas.sbtanks.physics.PhysicsContainer

class PlayerMovementControlSpec extends AnyFeatureSpec with GivenWhenThen with Matchers with Includes:
    info("As a player")
    info("I want to control the movement of my tank")
    info("so that I can navigate through the game map")

    Feature("Player controller") {
        Scenario("The player presses a directional movement input") {
            Given("An empty physics world")
            val physics = new Object() with PhysicsContainer 
            given PhysicsContainer = physics

            And("A tank with a movement behaviour starting at position (0, 0) with a step speed of 1")
            val startingPosition = (0, 0)
            val stepSpeed = 1
            val playerTank = PlayerTankBuilder()
                .setPosition(startingPosition(0), startingPosition(1))
                .build()
            playerTank.positionX should be (startingPosition(0))
            playerTank.positionY should be (startingPosition(1))

            And("A player controller for that tank")
            val controller = MockJFXPlayerTankController(playerTank)

            When("The player presses a movement input")
            controller.simulateInput(MockJFXPlayerTankController.MOVE_RIGHT_INPUT)

            And("A game step occours")
            controller.step(1)

            Then("The tank should move in the requested direction")
            (playerTank.positionX - startingPosition(0)) should be (1 / 16D)
            (playerTank.positionY - startingPosition(1)) should be (0)
        }
    }