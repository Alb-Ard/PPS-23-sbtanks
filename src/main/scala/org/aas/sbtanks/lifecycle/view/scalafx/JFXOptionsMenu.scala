package org.aas.sbtanks.lifecycle.view.scalafx

import org.aas.sbtanks.lifecycle.PointsManager
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.{Button, ButtonBar, TextField}
import scalafx.scene.layout.VBox
import scalafx.scene.paint.Color.{Brown, DarkGreen, DarkRed, Green, Red, SandyBrown}
import scalafx.scene.paint.{LinearGradient, Stops}
import scalafx.scene.text.{Font, Text, TextFlow}
import org.aas.sbtanks.lifecycle.{LevelSequencer, PointsManager}
import scalafx.scene.{Node, Scene}
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.{Button, ButtonBar}
import scalafx.scene.effect.DropShadow
import scalafx.scene.paint.Color.*
import scalafx.scene.paint.*
import scalafx.scene.text.{Font, Text, TextAlignment, TextFlow}
import scalafx.Includes.*

class JFXOptionsMenu extends VBox:

    val stylesheet = getClass.getResource("/ui/press_start_2p.ttf").toExternalForm

    padding = Insets(80, 70, 80, 70)
    children = Seq(
        new Text {
            text = "Set Your Username"
            font = Font.loadFont(stylesheet, 70)
            alignment = Pos.Center
            fill = new LinearGradient(
                endX = 0,
                stops = Stops(SandyBrown, Brown))
        },
        new TextField {
            text = "Put your username here"
            font = Font.loadFont(stylesheet, 30)
            alignment = Pos.Center
        },
        new ButtonBar {
            padding = Insets(100, 130, 100, 0)
            buttons = Seq(
                new Button("Reset High Score") {
                    font = Font.loadFont(stylesheet, 20)
                    id = "resetScore"
                    margin = Insets(0, 80, 0, 80)
                    onAction = () => {
                        PointsManager.resetHighScore()
                    }
                },
                new Button("Go Back To Title Screen") {
                    font = Font.loadFont(stylesheet, 20)
                    id = "titleScreen"
                    margin = Insets(0, 80, 0, 80)

                }
            )
        })
