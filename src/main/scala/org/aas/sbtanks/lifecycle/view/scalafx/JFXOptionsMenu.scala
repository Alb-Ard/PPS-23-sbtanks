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

    val stylesheet = getClass.getResource("/ui/press_start_2p.ttf").toExternalForm

    val resetHighScoreRequested = EventSource[Unit]
    val mainMenuRequested = EventSource[Unit]

    private val BUTTON_SIZE = (64, 8)
    private val BUTTON_ICON_SIZE = (6, 6)

    spacing = 8
    background = Background.Empty
    minWidth <== windowSize(0)
    prefWidth <== windowSize(0)
    alignment = Pos.Center

    private val setUsernameText = createText("Set Your Username")
    setUsernameText.alignmentInParent = Pos.Center
    setUsernameText.margin = Insets(50, 0, 0, 0)
    setUsernameText.fill = new LinearGradient(0, stops = Stops(SandyBrown, Brown))
    children.add(setUsernameText)

    private val setUsernameField = new TextField()
    setUsernameField.margin = Insets(0, 200, 0, 200)
    setUsernameField.alignmentInParent = Pos.Center
    children.add(setUsernameField)

    private val resetScoreButton = createButton("RESET HIGH SCORE")
    resetScoreButton.onMouseClicked = _ => resetHighScoreRequested(())
    resetScoreButton.alignmentInParent = Pos.Center

    private val returnMainButton = createButton("RETURN TO MAIN MENU")
    returnMainButton.onMouseClicked = _ => mainMenuRequested(())
    returnMainButton.alignmentInParent = Pos.Center

//    private val buttonBar = new ButtonBar()
//    buttonBar.buttons = Seq(resetScoreButton, returnMainButton)
//    buttonBar.alignmentInParent = Pos.Center
//    buttonBar.margin = Insets(0, 530, 0, 0)
//    children.add(buttonBar)


//    children = Seq(
//        new Text {
//            text = "Set Your Username"
//            font = Font.loadFont(stylesheet, 70)
//            alignment = Pos.Center
//            fill = new LinearGradient(
//                endX = 0,
//                stops = Stops(SandyBrown, Brown))
//        },
//        new TextField {
//            text = "Put your username here"
//            font = Font.loadFont(stylesheet, 30)
//            alignment = Pos.Center
//        },
//        new ButtonBar {
//            padding = Insets(100, 130, 100, 0)
//            buttons = Seq(
//                new Button("Reset High Score") {
//                    font = Font.loadFont(stylesheet, 20)
//                    id = "resetScore"
//                    margin = Insets(0, 80, 0, 80)
//                    onAction = () => {
//                        PointsManager.resetHighScore()
//                    }
//                },
//                new Button("Go Back To Title Screen") {
//                    font = Font.loadFont(stylesheet, 20)
//                    id = "titleScreen"
//                    margin = Insets(0, 80, 0, 80)
//
//                }
//            )
//        })

    private def createButton(text: String) = JFXViewComponentFactory.createButton(BUTTON_SIZE,
        BUTTON_ICON_SIZE,
        interfaceScale,
        text,
        Seq("main-menu-text", "main-menu-button"))

    private def createText(text: String) = JFXViewComponentFactory.createText(text, Seq("main-menu-text"))

