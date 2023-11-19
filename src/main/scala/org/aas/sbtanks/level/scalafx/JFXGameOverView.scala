package org.aas.sbtanks.level.scalafx

import org.aas.sbtanks.lifecycle.PointsManager
import scalafx.scene.Scene
import scalafx.application.JFXApp3
import scalafx.geometry.Insets
import scalafx.scene.control.{Button, ButtonBar}
import scalafx.scene.effect.DropShadow
import scalafx.scene.layout.HBox
import scalafx.scene.paint.Color.*
import scalafx.scene.paint.*
import scalafx.scene.text.Text
import scalafx.scene.text.Font

class JFXGameOverView:

    val stylesheet = getClass.getResource("/ui/press_start_2p.ttf").toExternalForm

    def generateContent(): HBox =
        new HBox {
            padding = Insets(50, 60, 50, 60)
            children = Seq(
                new Text {
                    text = "Game Over"
                    font = Font.loadFont(stylesheet, 30)
                    //style = "normal bold 100pt"
                    fill = new LinearGradient(
                        endX = 0,
                        stops = Stops(Red, DarkRed))
                },
                new Text {
                    text = "Score: " + PointsManager.amount
                    font = Font.loadFont(stylesheet, 30)
                    //style = "italic bold 100pt"
                    fill = new LinearGradient(
                        endX = 0,
                        stops = Stops(Green, DarkGreen)
                    )
                    effect = new DropShadow {
                        color = DarkGray
                        radius = 15
                        spread = 0.25
                    }
                },
                new ButtonBar {
                    buttons = Seq(
                        new Button("Retry") {
                            font = Font.loadFont(stylesheet, 10)
                            id = "retry"
                            style = "background-color: black"
                            //onMouseClicked = goes back to first level
                        },
                        new Button("Go to Title Screen") {
                            font = Font.loadFont(stylesheet, 10)
                            id = "title_screen"
                            //onMouseClicked = goes back to menu
                        }
                    )
                })
        }



object testJFXGameOver extends JFXApp3 with scalafx.Includes:
    val viewScale = 4D
    val tileSize = 16D
    val gameoverView = new JFXGameOverView


    override def start(): Unit =
        stage = new JFXApp3.PrimaryStage:
            title = "sbTanks"
            width = 1280
            height = 720
            scene = new Scene:
                fill = Color.Black
                content = gameoverView.generateContent()

