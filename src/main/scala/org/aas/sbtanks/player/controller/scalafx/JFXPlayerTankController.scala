package org.aas.sbtanks.player.controller.scalafx

import org.aas.sbtanks.entities.tank.view.TankView
import org.aas.sbtanks.entities.tank.controller.TankController
import org.aas.sbtanks.entities.tank.controller.TankController.ControllableTank

import scalafx.stage.Stage
import scalafx.scene.input.KeyEvent
import scalafx.scene.Node
import scalafx.Includes
import org.aas.sbtanks.entities.repository.EntityRepositoryContextAware
import org.aas.sbtanks.entities.repository.EntityRepositoryContext

abstract class JFXPlayerTankController(using context: EntityRepositoryContext[Stage])(tank: ControllableTank, speedMultiplier: Double, view: TankView, viewScale: Double)
    extends TankController(tank, speedMultiplier, view, viewScale, JFXPlayerInputController())
    with EntityRepositoryContextAware
    with Includes:

    registerSceneEventHandlers(context.viewContainer)

    private def registerSceneEventHandlers(stage: Stage) =
        stage.addEventHandler(KeyEvent.KeyPressed, inputEvents.handleKeyPressEvent)
        stage.addEventHandler(KeyEvent.KeyReleased, inputEvents.handleKeyReleasedEvent)

object JFXPlayerTankController:
    def factory(speedMultiplier: Double, viewScale: Double, bulletConsumer: (AnyRef, Node) => Any)(context: EntityRepositoryContext[Stage], tank: ControllableTank, view: TankView) =
        new JFXPlayerTankController(using context)(tank, speedMultiplier, view, viewScale):
            override def shoot() =
                // TODO
                //bulletConsumer()
                this