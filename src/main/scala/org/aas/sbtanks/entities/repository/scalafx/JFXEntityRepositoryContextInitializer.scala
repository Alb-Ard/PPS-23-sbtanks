package org.aas.sbtanks.entities.repository.scalafx

import org.aas.sbtanks.event.EventSource
import org.aas.sbtanks.player.view.ui.scalafx.JFXPlayerSidebarView
import org.aas.sbtanks.entities.repository.EntityRepositoryContextInitializer

import scalafx.stage.Stage
import scalafx.scene.layout.Pane
import scalafx.scene.layout.BorderPane
import scalafx.geometry.Pos
import scalafx.scene.Node
import scalafx.geometry.Insets
import scalafx.scene.layout.Priority
import scalafx.scene.layout.VBox

/**
  * Object containing context initializers that use ScalaFX
  */
object JFXEntityRepositoryContextInitializer extends scalafx.Includes:
    type JFXInitializer = EntityRepositoryContextInitializer[Stage, Pane]

    /**
      * Creates an initializer
      *
      * @param scenePane
      * @param controller
      * @param oldContainer
      * @return
      */
    def ofLevel: JFXInitializer =
        new Object() with JFXInitializer:
            override def createContainer(controller: Stage, oldUiContainer: Option[Pane], oldViewContainer: Option[Pane]) =
                val entityViewContainer = Pane()
                val uiContainer = VBox()
                val scenePane = BorderPane(entityViewContainer, null, uiContainer, null, null)
                BorderPane.setAlignment(entityViewContainer, Pos.CENTER)
                controller.scene.value.content.clear()
                controller.scene.value.content.add(scenePane)
                (Option(entityViewContainer), Option(uiContainer))

    def ofView: JFXInitializer =
        new Object() with JFXInitializer:
            override def createContainer(controller: Stage, oldUiContainer: Option[Pane], oldViewContainer: Option[Pane]) =
                val uiContainer = VBox()
                controller.scene.value.content.clear()
                controller.scene.value.content.add(uiContainer)
                (Option.empty, Option(uiContainer))