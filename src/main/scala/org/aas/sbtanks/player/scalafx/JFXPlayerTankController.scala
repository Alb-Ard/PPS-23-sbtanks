package org.aas.sbtanks.player.scalafx

import org.aas.sbtanks.player.PlayerTankController
import org.aas.sbtanks.entities.tank.view.TankView
import org.aas.sbtanks.player.ControllableTank

import scalafx.stage.Stage
import scalafx.scene.input.KeyEvent
import scalafx.Includes

class JFXPlayerTankController(tank: ControllableTank, view: TankView, viewScale: Double)
    extends PlayerTankController(tank, view, viewScale, JFXPlayerInputController())
    with Includes:

    def registerSceneEventHandlers(stage: Stage) =
        stage.addEventHandler(KeyEvent.KeyPressed, inputEvents.handleKeyPressEvent)
        stage.addEventHandler(KeyEvent.KeyReleased, inputEvents.handleKeyReleasedEvent)
