package org.aas.sbtanks.player

import org.scalatest.featurespec.AnyFeatureSpec
import org.scalatest.GivenWhenThen
import org.scalatest.matchers.should.Matchers

import _root_.scalafx.Includes
import _root_.scalafx.application.JFXApp3
import _root_.scalafx.stage.Stage
import _root_.scalafx.scene.input.KeyEvent
import _root_.scalafx.scene.image.Image

import javafx.scene.{input => jfxsi}
import javafx.scene.input.KeyCode.W
import javafx.scene.input.KeyCode.S
import javafx.scene.input.KeyCode.A
import javafx.scene.input.KeyCode.D

import org.aas.sbtanks.player.controller.scalafx.JFXPlayerInputController
import org.aas.sbtanks.player.controller.scalafx.JFXPlayerTankController
import org.aas.sbtanks.entities.tank.controller.TankController.ControllableTank
import org.aas.sbtanks.entities.tank.controller.TankInputController
import org.aas.sbtanks.entities.tank.view.scalafx.JFXTankView
import org.aas.sbtanks.entities.tank.view.TankView
import org.aas.sbtanks.entities.repository.EntityRepositoryContext

class MockTankView extends TankView:
    override def isMoving(value: Boolean): Unit = ()
    override def look(rotation: Double): Unit = ()
    override def move(x: Double, y: Double): Unit = ()

class MockJFXPlayerTankController(tank: ControllableTank) extends TankInputController(tank, MockTankView(), 1 / 16D, 1, JFXPlayerInputController()):
    def simulateInput(event: KeyEvent) = inputEvents.handleKeyPressEvent(event)
    override protected def shoot() = this

object MockJFXPlayerTankController:
    val MOCK_STAGE = EntityRepositoryContext[Stage, Any](new JFXApp3.PrimaryStage())

    val MOVE_UP_INPUT = KeyEvent(jfxsi.KeyEvent(jfxsi.KeyEvent.KEY_PRESSED, "w", "w", W, false, false, false, false))
    val MOVE_DOWN_INPUT = KeyEvent(jfxsi.KeyEvent(jfxsi.KeyEvent.KEY_PRESSED, "s", "s", S, false, false, false, false))
    val MOVE_LEFT_INPUT = KeyEvent(jfxsi.KeyEvent(jfxsi.KeyEvent.KEY_PRESSED, "a", "a", A, false, false, false, false))
    val MOVE_RIGHT_INPUT = KeyEvent(jfxsi.KeyEvent(jfxsi.KeyEvent.KEY_PRESSED, "d", "d", D, false, false, false, false))