package org.aas.sbtanks.player.controller.scalafx

import org.aas.sbtanks.entities.tank.view.TankView
import org.aas.sbtanks.entities.tank.controller.TankController
import org.aas.sbtanks.entities.tank.controller.TankController.ControllableTank

import scalafx.stage.Stage
import scalafx.scene.input.KeyEvent
import scalafx.Includes
import org.aas.sbtanks.entities.repository.EntityRepositoryContextAware
import org.aas.sbtanks.entities.repository.EntityRepositoryContext

class JFXPlayerTankController(using context: EntityRepositoryContext[Stage])(tank: ControllableTank, speedMultiplier: Double, view: TankView, viewScale: Double)
    extends TankController(tank, speedMultiplier, view, viewScale, JFXPlayerInputController())
    with EntityRepositoryContextAware
    with Includes:

    registerSceneEventHandlers(context.viewContainer)

    private def registerSceneEventHandlers(stage: Stage) =
        stage.addEventHandler(KeyEvent.KeyPressed, inputEvents.handleKeyPressEvent)
        stage.addEventHandler(KeyEvent.KeyReleased, inputEvents.handleKeyReleasedEvent)

object JFXPlayerTankController:
    def factory(speedMultiplier: Double, viewScale: Double)(context: EntityRepositoryContext[Stage], tank: ControllableTank, view: TankView) =
        JFXPlayerTankController(using context)(tank, speedMultiplier, view, viewScale)