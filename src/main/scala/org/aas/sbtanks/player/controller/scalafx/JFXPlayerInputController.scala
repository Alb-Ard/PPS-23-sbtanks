package org.aas.sbtanks.player.controller.scalafx

import org.aas.sbtanks.player.controller.PlayerInputEvents

import scalafx.scene.input.KeyEvent
import scalafx.scene.input.KeyCode
import scalafx.scene.input.KeyCode.W
import scalafx.scene.input.KeyCode.S
import scalafx.scene.input.KeyCode.A
import scalafx.scene.input.KeyCode.D
import scalafx.scene.input.KeyCode.Space

class JFXPlayerInputController extends PlayerInputEvents:
    private var inputX = 0D
    private var inputY = 0D
    private var shootPressed = false

    def handleKeyPressEvent(event: KeyEvent): Unit =
        if event.isMoveEvent then
            inputX = clampAxis(inputX + axisValue(event, A, D))
            inputY = clampAxis(inputY + axisValue(event, W, S))
            invokeMoved()
        else if event.isShootEvent then
            val wasShootPressed = shootPressed
            shootPressed = true
            if !wasShootPressed && shootPressed then
                shootPerfomed(())
    
    def handleKeyReleasedEvent(event: KeyEvent): Unit =
        if event.isMoveEvent then
            inputX = clampAxis(inputX - axisValue(event, A, D))
            inputY = clampAxis(inputY - axisValue(event, W, S))
            invokeMoved()
        else if event.isShootEvent then
            shootPressed = false

    private def invokeMoved():  Unit =
        inputX match
            case 0 => moveDirectionChanged(0, inputY)
            case x => moveDirectionChanged(x, 0)
            
    private def axisValue(event: KeyEvent, negative: KeyCode, positive: KeyCode): Double =
        event.code match
            case p if p == negative => -1D
            case n if n == positive => 1D
            case _ => 0D

    private def clampAxis(value: Double) = Math.min(1, Math.max(-1, value))

    extension (event: KeyEvent)
        def isMoveEvent =
            event.code match
                case W => true
                case S => true
                case A => true
                case D => true
                case _ => false

        def isShootEvent = 
            event.code == Space