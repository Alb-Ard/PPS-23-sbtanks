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
import org.aas.sbtanks.entities.tank.controller.TankController.ControllableTank
import org.aas.sbtanks.entities.tank.view.scalafx.JFXTankView

class MockJFXPlayerTankController(tank: ControllableTank) extends JFXPlayerTankController(tank, 1 / 16D, JFXTankView(Seq()), 1):
    def simulateInput(event: KeyEvent) = inputEvents.handleKeyPressEvent(event)

object MockJFXPlayerTankController:
    val moveUpInput = KeyEvent(KeyEvent.KEY_PRESSED, "w", "w", W, false, false, false, false)
    val moveDownInput = KeyEvent(KeyEvent.KEY_PRESSED, "s", "s", S, false, false, false, false)
    val moveLeftInput = KeyEvent(KeyEvent.KEY_PRESSED, "a", "a", A, false, false, false, false)
    val moveRightInput = KeyEvent(KeyEvent.KEY_PRESSED, "d", "d", D, false, false, false, false)