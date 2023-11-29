package org.aas.sbtanks.lifecycle.view.scalafx

import scalafx.scene.layout.GridPane
import scalafx.scene.image.ImageView
import scalafx.scene.image.Image
import scalafx.geometry.HPos
import scalafx.geometry.Insets
import scalafx.scene.control.Button
import scalafx.scene.Node
import org.aas.sbtanks.event.EventSource
import scalafx.scene.paint.Color
import scalafx.scene.layout.Background
import org.aas.sbtanks.common.view.scalafx.JFXViewComponentFactory

/**
  * View shown to the player when starting the application. Lets the user start a new game, go to the settings view and exit the application
  *
  * @param interfaceScale A scale sizing factor for the interface
  */
class JFXMainMenu(interfaceScale: Double) extends GridPane:
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

    vgap = 16;

    background = Background.EMPTY

    private val titleImage = withGridCell(ImageView(Image("ui/title.png", TITLE_IMAGE_SIZE(0) * interfaceScale, TITLE_IMAGE_SIZE(1) * interfaceScale, true, false, false)), 0, 0)
    GridPane.setHalignment(titleImage, HPos.CENTER)
    children.add(titleImage)

    private val startSinglePlayerButton = withGridCell(createButton("1 PLAYER"), 1, 0)
    startSinglePlayerButton.onMouseClicked = _ => startSinglePlayerGameRequested(())
    GridPane.setHalignment(startSinglePlayerButton, HPos.CENTER)
    children.add(startSinglePlayerButton)

    private val optionsButton = withGridCell(createButton("OPTIONS"), 2, 0)
    optionsButton.onMouseClicked = _ => optionsRequested(())
    GridPane.setHalignment(optionsButton, HPos.CENTER)
    children.add(optionsButton)

    private val quitButton = withGridCell(createButton("QUIT"), 3, 0)
    quitButton.onMouseClicked = _ => quitRequested(())
    GridPane.setHalignment(quitButton, HPos.CENTER)
    children.add(quitButton)

    private def withGridCell(node: Node, row: Int, column: Int) =
        GridPane.setRowIndex(node, row)
        GridPane.setColumnIndex(node, column)
        node

    private def createButton(text: String) = JFXViewComponentFactory.createButton(BUTTON_SIZE, BUTTON_ICON_SIZE, interfaceScale, text)

