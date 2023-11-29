package org.aas.sbtanks.lifecycle.view.scalafx

import org.aas.sbtanks.common.Pausable
import org.aas.sbtanks.common.view.scalafx.JFXViewComponentFactory
import org.aas.sbtanks.entities.repository.context.EntityRepositoryContext
import org.aas.sbtanks.entities.repository.context.EntityRepositoryContextAware
import org.aas.sbtanks.lifecycle.GameLoop
import scalafx.Includes
import scalafx.scene.input.KeyCode
import scalafx.scene.input.KeyEvent
import scalafx.scene.layout.Background
import scalafx.scene.layout.Pane
import scalafx.scene.layout.VBox
import scalafx.scene.paint.Color
import scalafx.scene.text.Text
import scalafx.stage.Stage
import org.aas.sbtanks.event.EventSource

/**
  * A view for a pause menu that lets the player resume the game or quit to the main menu
  *
  * @param interfaceScale An interface scaling factor
  */
class JFXPauseMenu(interfaceScale: Double) extends VBox:
    private val BUTTON_SIZE = (64, 8)
    private val BUTTON_ICON_SIZE = (6, 6)

    val resumeRequested = EventSource[Unit]
    val quitRequested = EventSource[Unit]

    background = Background.fill(Color.BLACK)

    spacing = 8

    private val pauseText = new Text("PAUSED"):
        styleClass.add("main-menu-text")
    children.add(pauseText)

    private val resumeButton = JFXViewComponentFactory.createButton(BUTTON_SIZE, BUTTON_ICON_SIZE, interfaceScale, "RESUME")
    resumeButton.onMouseClicked = _ => resumeRequested(())
    children.add(resumeButton)

    private val quitButton = JFXViewComponentFactory.createButton(BUTTON_SIZE, BUTTON_ICON_SIZE, interfaceScale, "QUIT")
    quitButton.onMouseClicked = _ => quitRequested(())
    children.add(quitButton)