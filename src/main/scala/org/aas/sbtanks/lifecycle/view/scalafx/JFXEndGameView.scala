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
 * View shown to the player when it reaches a game end, either in game over (by losing all his lives or by the destruction of the player's base) or by winning (by completing all levels)
 *
 * @param completedLevelsCount The amount of the levels the player completed.
 * @param points The amount if points the player has obtained during this game.
 * @param isGameOver If the vied should be displayed as a game over.
 * @param interfaceScale The scale of the interface.
 * @param windowSize The size of the window.
 */
class JFXEndGameView(completedLevelsCount: Int, points: Int, isGameOver: Boolean, interfaceScale: Double, windowSize: (IntegerProperty, IntegerProperty)) extends VBox:
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

    private val mainText = createText(if isGameOver then "GAME OVER" else "YOU WIN!")
    mainText.alignmentInParent = Pos.Center
    mainText.margin = Insets(50, 0, 20, 0)
    mainText.fill = new LinearGradient(0, stops = Stops(Red, DarkRed))
    children.add(mainText)

    if isGameOver then
        val levelCountText = createText("YOU DIED AT LEVEL: " + (completedLevelsCount + 1))
        levelCountText.alignmentInParent = Pos.Center
        levelCountText.margin = Insets(50, 0, 20, 0)
        levelCountText.fill = new LinearGradient(0, stops = Stops(Red, DarkRed))
        children.add(levelCountText)

    private val gameScore = createText("Score: " + points + "  ")
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