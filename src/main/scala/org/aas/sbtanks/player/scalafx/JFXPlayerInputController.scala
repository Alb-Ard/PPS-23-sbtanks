package org.aas.sbtanks.player.scalafx

import org.aas.sbtanks.player.PlayerInputEvents
import scalafx.scene.input.KeyEvent
import scalafx.scene.input.KeyCode.W
import scalafx.scene.input.KeyCode.S
import scalafx.scene.input.KeyCode.A
import scalafx.scene.input.KeyCode.D

class JFXPlayerInputController extends PlayerInputEvents:
    def handleKeyPressEvent(event: KeyEvent): Unit =
        event.code match
            case W => moved(0, -1)
            case S => moved(0, 1)
            case A => moved(-1, 0)
            case D => moved(1, 0)
            case _ => ()
    
    def handleKeyReleasedEvent(event: KeyEvent): Unit =
        moved(0, 0)