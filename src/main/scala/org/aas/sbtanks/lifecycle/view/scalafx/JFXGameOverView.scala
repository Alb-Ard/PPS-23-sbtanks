package org.aas.sbtanks.lifecycle.view.scalafx

import javafx.event.EventHandler
import org.aas.sbtanks.common.view.scalafx.JFXViewComponentFactory
import org.aas.sbtanks.event.EventSource
import org.aas.sbtanks.lifecycle.{LevelSequencer, PointsManager, SavedDataManager}
import scalafx.Includes.*
import scalafx.beans.property.IntegerProperty
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.{Button, ButtonBar}
import scalafx.scene.effect.DropShadow
import scalafx.scene.input.MouseEvent
import scalafx.scene.layout.{Background, VBox}
import scalafx.scene.paint.*
import scalafx.scene.paint.Color.*
import scalafx.scene.text.TextAlignment.Center
import scalafx.scene.text.{Font, Text, TextAlignment, TextFlow}
import scalafx.scene.{Node, Scene}

/**
 * View shown to the player when it reaches a game over status, either by losing all his lives or by the destruction of the player's base.
 *
 * @param level The levelsequencer in order to retrieve the number of levels completed.
 * @param interfaceScale The scale of the interface.
 * @param windowSize The size of the window.
 */
class JFXGameOverView(level: LevelSequencer[?,?], interfaceScale: Double, windowSize: (IntegerProperty, IntegerProperty)) extends VBox:

    /**
     * event that is called when the player wants to restart the game.
     */
    val retryRequested = EventSource[Unit]
    /**
     * event that is called when the player wants to quit the game.
     */
    val exitRequested = EventSource[Unit]

    private val BUTTON_SIZE = (150, 10)
    private val BUTTON_ICON_SIZE = (6, 6)

    spacing = 8
    background = Background.Empty
    minWidth <== windowSize(0)
    prefWidth <== windowSize(0)
    alignment = Pos.Center

    private val gameOverText = createText("GAME OVER")
    gameOverText.alignmentInParent = Pos.Center
    gameOverText.margin = Insets(50, 0, 20, 0)
    gameOverText.fill = new LinearGradient(0, stops = Stops(Red, DarkRed))
    children.add(gameOverText)

    private val levelDeathText = createText("YOU DIED AT LEVEL: " + (level.completedLevelCount + 1))
    levelDeathText.alignmentInParent = Pos.Center
    levelDeathText.margin = Insets(50, 0, 20, 0)
    levelDeathText.fill = new LinearGradient(0, stops = Stops(Red, DarkRed))
    children.add(levelDeathText)

    private val gameScore = createText("Score: " + PointsManager.amount + "  ")
    gameScore.alignmentInParent = Pos.Center
    gameScore.fill = new LinearGradient(0, stops = Stops(Green, DarkGreen))

    private val bestScore = createText("High Score: " + SavedDataManager.highScore)
    bestScore.alignmentInParent = Pos.Center
    bestScore.fill = new LinearGradient(0, stops = Stops(Green, DarkGreen))

    private val scoreFlow = new TextFlow()
    scoreFlow.children = Seq(gameScore, bestScore)
    scoreFlow.margin = Insets(50, 50, 0, 50)
    scoreFlow.alignmentInParent = Pos.Center
    scoreFlow.textAlignment = Center
    children.add(scoreFlow)

    private val restartButton = createButton("RETRY")
    restartButton.onMouseClicked = _ => retryRequested(())
    restartButton.alignmentInParent = Pos.Center

    private val exitButton = createButton("QUIT")
    exitButton.onMouseClicked = _ => exitRequested(())
    exitButton.alignmentInParent = Pos.Center

    private val buttonBar = new ButtonBar()
    buttonBar.buttons = Seq(restartButton, exitButton)
    buttonBar.alignmentInParent = Pos.Center
    buttonBar.margin = Insets(50, 200, 0, 200)
    children.add(buttonBar)

    private def createButton(text: String) = JFXViewComponentFactory.createButton(BUTTON_SIZE,
        BUTTON_ICON_SIZE,
        interfaceScale,
        text,
        Seq("main-menu-text", "main-menu-button"))

    private def createText(text: String) = JFXViewComponentFactory.createText(text, Seq("main-menu-text"))