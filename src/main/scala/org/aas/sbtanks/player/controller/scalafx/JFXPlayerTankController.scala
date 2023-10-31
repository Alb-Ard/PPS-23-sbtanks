package org.aas.sbtanks.player.controller.scalafx

import org.aas.sbtanks.entities.tank.view.TankView
import org.aas.sbtanks.entities.tank.controller.TankController
import org.aas.sbtanks.entities.tank.controller.TankController.ControllableTank

import scalafx.stage.Stage
import scalafx.scene.input.KeyEvent
import scalafx.Includes

class JFXPlayerTankController(tank: ControllableTank, speedMultiplier: Double, view: TankView, viewScale: Double)
    extends TankController(tank, speedMultiplier, view, viewScale, JFXPlayerInputController())
    with Includes:

    def registerSceneEventHandlers(stage: Stage) =
        stage.addEventHandler(KeyEvent.KeyPressed, inputEvents.handleKeyPressEvent)
        stage.addEventHandler(KeyEvent.KeyReleased, inputEvents.handleKeyReleasedEvent)
