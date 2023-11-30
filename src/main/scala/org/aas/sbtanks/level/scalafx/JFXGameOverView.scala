package org.aas.sbtanks.level.scalafx

import javafx.event.EventHandler
import javafx.scene.input
import org.aas.sbtanks.lifecycle.{LevelSequencer, PointsManager}
import scalafx.scene.{Node, Scene}
import scalafx.application.JFXApp3
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.{Button, ButtonBar}
import scalafx.scene.effect.DropShadow
import scalafx.scene.input.MouseEvent
import scalafx.scene.layout.VBox
import scalafx.scene.paint.Color.*
import scalafx.scene.paint.*
import scalafx.scene.text.{Font, Text, TextAlignment, TextFlow}
import scalafx.Includes.*

class JFXGameOverView(level: LevelSequencer[?,?]) extends VBox:

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
            text = "You died at Level: " + (level.completedLevelCount + 1)
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
                    text = "Highest Score: " + PointsManager.highScore
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
                    onAction = () => {
                        PointsManager.resetAmount()
                        //TO DO - RESTART THE GAME
                        //LevelSequencer[AnyRef, Node]()
                    }
                },
                new Button("Quit") {
                    font = Font.loadFont(stylesheet, 20)
                    id = "title_screen"
                    margin = Insets(0, 80, 0, 80)
                    onAction = () => System.exit(0)
                }
            )
        })