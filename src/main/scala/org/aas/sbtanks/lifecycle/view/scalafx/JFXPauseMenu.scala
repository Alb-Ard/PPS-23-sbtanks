package org.aas.sbtanks.lifecycle.view.scalafx

import org.aas.sbtanks.lifecycle.GameLoop
import scalafx.stage.Stage
import scalafx.scene.input.KeyEvent
import scalafx.Includes
import scalafx.scene.input.KeyCode
import org.aas.sbtanks.entities.repository.context.EntityRepositoryContextAware
import org.aas.sbtanks.entities.repository.context.EntityRepositoryContext
import scalafx.scene.layout.Pane
import org.aas.sbtanks.common.Pausable

class JFXPauseMenu[CVSlotKey, CVSlot](using context: EntityRepositoryContext[Stage, CVSlotKey, CVSlot])(gameLoop: GameLoop)
    extends Includes with EntityRepositoryContextAware[Stage, CVSlotKey, CVSlot]:
        
    registerSceneViewHandlers(context.viewController)

    private def registerSceneViewHandlers(stage: Stage) =
        stage.addEventHandler(KeyEvent.KeyPressed, onKeyPressed)

    private def onKeyPressed(event: KeyEvent) =
        event.code match
            case KeyCode.ESCAPE =>
                event.consume()
                gameLoop.togglePaused()
            case _ => ()

