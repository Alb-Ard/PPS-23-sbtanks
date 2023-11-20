package org.aas.sbtanks.level.scalafx

import org.aas.sbtanks.lifecycle.PointsManager
import scalafx.scene.Scene
import scalafx.application.JFXApp3
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.{Button, ButtonBar}
import scalafx.scene.effect.DropShadow
import scalafx.scene.layout.{VBox}
import scalafx.scene.paint.Color.*
import scalafx.scene.paint.*
import scalafx.scene.text.{Font, Text, TextAlignment, TextFlow}

class JFXGameOverView extends VBox:

    val stylesheet = getClass.getResource("/ui/press_start_2p.ttf").toExternalForm

    padding = Insets(80, 70, 80, 70)
    children = Seq(
        new Text {
            text = "Game Over"
            font = Font.loadFont(stylesheet, 120)
            alignment = Pos.Center
            fill = new LinearGradient(
                endX = 0,
                stops = Stops(Red, DarkRed))
        },
        new Text {
            text = "You died at Level: "
            font = Font.loadFont(stylesheet, 30)
            alignment = Pos.Center
            fill = new LinearGradient(
                endX = 0,
                stops = Stops(Red, DarkRed))
        },
        new TextFlow {
            padding = Insets(40, 40, 40, 40)
            children = Seq(
                new Text {
                    text = "Score: " + PointsManager.amount
                    font = Font.loadFont(stylesheet, 40)
                    fill = new LinearGradient(
                        endX = 0,
                        stops = Stops(Green, DarkGreen)
                    )
                    margin = Insets(0, 0, 0, 70)
                },
                new Text {
                    text = "Highest Score: " + PointsManager.amount
                    font = Font.loadFont(stylesheet, 40)
                    fill = new LinearGradient(
                        endX = 0,
                        stops = Stops(Green, DarkGreen)
                    )
                    margin = Insets(0, 70, 0, 0)
                }
            )
        },
        new ButtonBar {
            padding = Insets(100, 130, 100, 0)
            buttons = Seq(
                new Button("Retry") {
                    font = Font.loadFont(stylesheet, 20)
                    id = "retry"
                    margin = Insets(0, 80, 0, 80)
                    //onMouseClicked = goes back to first level
                },
                new Button("Go to Title") {
                    font = Font.loadFont(stylesheet, 20)
                    id = "title_screen"
                    margin = Insets(0, 80, 0, 80)
                    //onMouseClicked = goes back to menu
                }
            )
        })

object testJFXGameOver extends JFXApp3 with scalafx.Includes:
    val viewScale = 4D
    val tileSize = 16D


    override def start(): Unit =
        stage = new JFXApp3.PrimaryStage:
            title = "sbTanks"
            width = 1280
            height = 720
            scene = new Scene:
                fill = Color.Black
                content = new JFXGameOverView

