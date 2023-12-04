package org.aas.sbtanks.lifecycle.controller.scalafx

import scalafx.stage.Stage
import scalafx.scene.input.KeyEvent
import scalafx.scene.input.KeyCode
import scalafx.scene.layout.Pane
import scalafx.Includes

import org.aas.sbtanks.entities.repository.context.EntityRepositoryContext
import org.aas.sbtanks.lifecycle.GameLoop
import org.aas.sbtanks.entities.repository.context.EntityRepositoryContextAware
import org.aas.sbtanks.lifecycle.view.scalafx.JFXPauseMenu
import org.aas.sbtanks.common.Steppable
import org.aas.sbtanks.common.ViewSlot

abstract class JFXPauseController(using context: EntityRepositoryContext[Stage, ViewSlot, Pane])(gameLoop: GameLoop, view: JFXPauseMenu)
    extends Steppable with Includes with EntityRepositoryContextAware[Stage, ViewSlot, Pane]:

    registerSceneViewHandlers(context.viewController)
    context.viewSlots.get(ViewSlot.Overlay).foreach(s => s.children.add(view))
    view.resumeRequested += { _ => gameLoop.setPaused(false) }
    view.quitRequested += { _ => quit() }
    gameLoop.pauseChanged += view.visible.set

    override def step(delta: Double) = this

    protected def quit(): Unit

    private def registerSceneViewHandlers(stage: Stage) =
        stage.addEventHandler(KeyEvent.KeyPressed, onKeyPressed)

    private def onKeyPressed(event: KeyEvent) =
        event.code match
            case KeyCode.ESCAPE =>
                event.consume()
                gameLoop.togglePaused()
            case _ => ()