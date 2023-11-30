package org.aas.sbtanks.lifecycle.view.scalafx

import org.aas.sbtanks.common.view.scalafx.JFXViewComponentFactory
import org.aas.sbtanks.event.EventSource
import org.aas.sbtanks.lifecycle.PointsManager
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.{Button, ButtonBar, TextField}
import scalafx.scene.layout.{Background, VBox}
import scalafx.scene.paint.Color.{Brown, DarkGreen, DarkRed, Green, Red, SandyBrown}
import scalafx.scene.paint.{LinearGradient, Stops, *}
import scalafx.scene.text.{Font, Text, TextFlow}
import org.aas.sbtanks.lifecycle.{LevelSequencer, PointsManager}
import scalafx.scene.{Node, Scene}
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.{Button, ButtonBar}
import scalafx.scene.effect.DropShadow
import scalafx.scene.paint.Color.*
import scalafx.scene.text.{Font, Text, TextAlignment, TextFlow}
import scalafx.Includes.*
import scalafx.beans.property.IntegerProperty
/**
 * View shown to the player when choosing the option select from main menu. Lets the user change its username, reset the best
 * score or go back to the main menu
 *
 */
class JFXOptionsMenu(interfaceScale: Double, windowSize: (IntegerProperty, IntegerProperty)) extends VBox:

    val resetHighScoreRequested = EventSource[Unit]
    val mainMenuRequested = EventSource[Unit]

    private val BUTTON_SIZE = (150, 10)
    private val BUTTON_ICON_SIZE = (6, 6)

    spacing = 8
    background = Background.Empty
    minWidth <== windowSize(0)
    prefWidth <== windowSize(0)
    alignment = Pos.Center

    private val setUsernameText = createText("Set Your Username")
    setUsernameText.alignmentInParent = Pos.Center
    setUsernameText.margin = Insets(50, 0, 20, 0)
    setUsernameText.fill = new LinearGradient(0, stops = Stops(SandyBrown, Brown))
    children.add(setUsernameText)

    private val setUsernameField = createTextField()
    setUsernameField.margin = Insets(0, 200, 0, 200)
    setUsernameField.alignmentInParent = Pos.Center
    children.add(setUsernameField)

    private val resetScoreButton = createButton("RESET HIGH SCORE")
    resetScoreButton.onMouseClicked = _ => resetHighScoreRequested(())
    resetScoreButton.alignmentInParent = Pos.Center
    //children.add(resetScoreButton)

    private val returnMainButton = createButton("RETURN TO MAIN MENU")
    returnMainButton.onMouseClicked = _ => mainMenuRequested(())
    returnMainButton.alignmentInParent = Pos.Center
    //children.add(returnMainButton)

    private val buttonBar = new ButtonBar()
    buttonBar.buttons = Seq(resetScoreButton, returnMainButton)
    buttonBar.alignmentInParent = Pos.Center
    buttonBar.margin = Insets(50, 200, 0, 200)
    children.add(buttonBar)

    private def createButton(text: String) = JFXViewComponentFactory.createButton(BUTTON_SIZE,
        BUTTON_ICON_SIZE,
        interfaceScale,
        text,
        Seq("main-menu-text", "main-menu-button"))

    private def createText(text: String) = JFXViewComponentFactory.createText(text, Seq("main-menu-text"))

    private def createTextField() = JFXViewComponentFactory.createTextField(Seq("main-menu-text"))

