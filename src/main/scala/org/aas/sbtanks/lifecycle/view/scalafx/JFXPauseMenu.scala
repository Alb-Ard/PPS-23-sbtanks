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
import scalafx.geometry.Pos
import scalafx.scene.layout.Priority
import scalafx.beans.property.IntegerProperty
import scalafx.geometry.Insets
import scalafx.scene.text.TextAlignment

/**
  * A view for a pause menu that lets the player resume the game or quit to the main menu
  *
  * @param interfaceScale An interface scaling factor
  */
class JFXPauseMenu(interfaceScale: Double, windowSize: (IntegerProperty, IntegerProperty)) extends VBox:
    private val BUTTON_SIZE = (64, 8)
    private val BUTTON_ICON_SIZE = (6, 6)

    val resumeRequested = EventSource[Unit]
    val quitRequested = EventSource[Unit]

    background = Background.fill(Color.Black)
    spacing = 8
    prefWidth <== windowSize(0)
    prefHeight <== windowSize(1)
    alignment = Pos.Center

    private val pauseText = createText("PAUSED")
    pauseText.alignmentInParent = Pos.Center
    pauseText.hgrow = Priority.ALWAYS
    pauseText.margin = Insets(0, 0, 50, 0)
    pauseText.textAlignment = TextAlignment.Center
    children.add(pauseText)

    private val resumeButton = createButton("RESUME")
    resumeButton.onMouseClicked = _ => resumeRequested(())
    resumeButton.alignmentInParent = Pos.Center
    resumeButton.hgrow = Priority.ALWAYS
    children.add(resumeButton)

    private val quitButton = createButton("QUIT")
    quitButton.onMouseClicked = _ => quitRequested(())
    quitButton.alignmentInParent = Pos.Center
    quitButton.hgrow = Priority.ALWAYS
    children.add(quitButton)

    private def createButton(text: String) = JFXViewComponentFactory.createButton(BUTTON_SIZE,
        BUTTON_ICON_SIZE,
        interfaceScale,
        text,
        Seq("main-menu-text", "main-menu-button"))

    private def createText(text: String) = JFXViewComponentFactory.createText(text, Seq("main-menu-text"))