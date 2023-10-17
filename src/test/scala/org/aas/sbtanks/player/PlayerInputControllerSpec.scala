package org.aas.sbtanks.player

import org.scalatest.flatspec.AnyFlatSpec
import org.aas.sbtanks.player.scalafx.JFXPlayerInputController
import org.scalatest.matchers.should.Matchers
import javafx.scene.input.KeyEvent
import javafx.scene.input.KeyCode
import _root_.scalafx.Includes

class PlayerInputControllerSpec extends AnyFlatSpec with Matchers with Includes:
    
    "A Player Controller" should "respond with a move event when receiving a movement input" in {
        val controller = JFXPlayerInputController()
        var eventReceived = false
        controller.movementInputListeners.addOne { (x, y) =>
            x should be (0)
            y should be (1)
            eventReceived = true
        }
        controller.handleKeyPressEvent(KeyEvent(KeyEvent.KEY_PRESSED, "w", "w", KeyCode.W, false, false, false, false))
        eventReceived should be (true)
    }