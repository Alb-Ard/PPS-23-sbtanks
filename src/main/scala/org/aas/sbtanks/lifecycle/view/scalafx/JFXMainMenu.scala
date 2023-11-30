package org.aas.sbtanks.lifecycle.view.scalafx

import org.aas.sbtanks.common.view.scalafx.JFXViewComponentFactory
import org.aas.sbtanks.event.EventSource
import org.aas.sbtanks.event.scalafx.JFXEventSource.*
import org.aas.sbtanks.lifecycle.PointsManager
import scalafx.geometry.HPos
import scalafx.scene.Node
import scalafx.scene.control.Button
import scalafx.scene.image.Image
import scalafx.scene.image.ImageView
import scalafx.scene.layout.Background
import scalafx.scene.paint.{Color, LinearGradient, Stops}
import scalafx.scene.layout.ColumnConstraints
import scalafx.beans.property.IntegerProperty
import scalafx.scene.text.Text
import scalafx.beans.property.StringProperty
import scalafx.scene.layout.VBox
import scalafx.geometry.Insets
import scalafx.scene.layout.Priority
import scalafx.geometry.Pos
import scalafx.beans.binding.Bindings
import scalafx.scene.paint.Color.{DarkGreen, Green, White}

/**
  * View shown to the player when starting the application. Lets the user start a new game, go to the settings view and exit the application
  *
  * @param interfaceScale A scale sizing factor for the interface
  */
class JFXMainMenu(interfaceScale: Double, windowSize: (IntegerProperty, IntegerProperty)) extends VBox:
    /**
      * Event invoked when the user requests to start a new single-player game
      */
    val startSinglePlayerGameRequested = EventSource[Unit]
    /**
      * Event invoked when the user request to see the options view
      */
    val optionsRequested = EventSource[Unit]
    /**
      * Event invoked when the user request to quit the application
      */
    val quitRequested = EventSource[Unit]

    private val TITLE_IMAGE_SIZE = (127, 64)
    private val BUTTON_SIZE = (64, 8)
    private val BUTTON_ICON_SIZE = (6, 6)

    spacing = 8
    background = Background.EMPTY
    minWidth <== windowSize(0)
    prefWidth <== windowSize(0)
    alignment = Pos.Center

    private val highScoreText = createText("HI- 0")
    private val highScoreProperty = PointsManager.highScoreChanged.toIntProperty()
    highScoreText.text <== Bindings.createStringBinding(() => "HI- " + highScoreProperty.value, highScoreProperty)
    highScoreText.alignmentInParent = Pos.Center
    highScoreText.fill = new LinearGradient(0, stops = Stops(White, White))
    highScoreText.margin = Insets(20, 0, 20, 0)
    children.add(highScoreText)

    private val titleImage = ImageView(Image("ui/title.png", TITLE_IMAGE_SIZE(0) * interfaceScale, TITLE_IMAGE_SIZE(1) * interfaceScale, true, false, false))
    VBox.setVgrow(titleImage, Priority.Always)
    titleImage.alignmentInParent = Pos.Center
    children.add(titleImage)

    private val startSinglePlayerButton = createButton("1 PLAYER")
    startSinglePlayerButton.onMouseClicked = _ => startSinglePlayerGameRequested(())
    startSinglePlayerButton.alignmentInParent = Pos.Center
    children.add(startSinglePlayerButton)

    private val optionsButton = createButton("OPTIONS")
    optionsButton.onMouseClicked = _ => optionsRequested(())
    optionsButton.alignmentInParent = Pos.Center
    children.add(optionsButton)

    private val quitButton = createButton("QUIT")
    quitButton.onMouseClicked = _ => quitRequested(())
    quitButton.alignmentInParent = Pos.Center
    children.add(quitButton)

    private def createButton(text: String) = JFXViewComponentFactory.createButton(BUTTON_SIZE,
        BUTTON_ICON_SIZE,
        interfaceScale,
        text,
        Seq("main-menu-text", "main-menu-button"))

    private def createText(text: String) = JFXViewComponentFactory.createText(text, Seq("main-menu-text"))