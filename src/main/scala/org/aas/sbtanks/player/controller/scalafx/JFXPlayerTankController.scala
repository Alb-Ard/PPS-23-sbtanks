package org.aas.sbtanks.player.controller.scalafx

import org.aas.sbtanks.entities.tank.view.TankView
import org.aas.sbtanks.entities.tank.controller.TankInputController
import org.aas.sbtanks.entities.tank.controller.TankController.ControllableTank
import org.aas.sbtanks.entities.repository.EntityRepositoryContextAware
import org.aas.sbtanks.entities.repository.EntityRepositoryContext

import scalafx.stage.Stage
import scalafx.scene.input.KeyEvent
import scalafx.scene.Node
import scalafx.scene.layout.Pane
import scalafx.Includes

abstract class JFXPlayerTankController(using context: EntityRepositoryContext[Stage, Pane])(tank: ControllableTank, speedMultiplier: Double, view: TankView, viewScale: Double)
    extends TankInputController(tank, view, speedMultiplier, viewScale, JFXPlayerInputController())
    with EntityRepositoryContextAware[Stage, Pane]
    with Includes:

    registerSceneEventHandlers(context.viewController)

    private def registerSceneEventHandlers(stage: Stage) =
        stage.addEventHandler(KeyEvent.KeyPressed, inputEvents.handleKeyPressEvent)
        stage.addEventHandler(KeyEvent.KeyReleased, inputEvents.handleKeyReleasedEvent)

object JFXPlayerTankController:
    def factory(speedMultiplier: Double, viewScale: Double, bulletConsumer: (AnyRef, Node) => Any)(context: EntityRepositoryContext[Stage, Pane], tank: ControllableTank, view: TankView) =
        new JFXPlayerTankController(using context)(tank, speedMultiplier, view, viewScale):
            override def shoot() =
                // TODO
                //bulletConsumer()
                this