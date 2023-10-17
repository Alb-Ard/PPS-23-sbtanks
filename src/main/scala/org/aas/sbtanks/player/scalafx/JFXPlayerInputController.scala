package org.aas.sbtanks.player.scalafx

import org.aas.sbtanks.player.PlayerInputEvents
import scalafx.scene.input.KeyEvent
import scalafx.scene.input.KeyCode.W
import scalafx.scene.input.KeyCode.S
import scalafx.scene.input.KeyCode.A
import scalafx.scene.input.KeyCode.D

class JFXPlayerInputController extends PlayerInputEvents:
    private def receiveMove(amountX: Int, amountY: Int): Unit = 
        invokeListeners(movementInputListeners) { listener => listener(amountX, amountY) }

    def handleKeyPressEvent(event: KeyEvent): Unit =
        event.code match
            case W => receiveMove(0, 1)
            case S => receiveMove(0, -1)
            case A => receiveMove(-1, 0)
            case D => receiveMove(1, 0)
            case _ => ()
        