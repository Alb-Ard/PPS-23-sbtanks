package org.aas.sbtanks.entities.repository.context.scalafx

import org.aas.sbtanks.event.EventSource
import org.aas.sbtanks.player.view.ui.scalafx.JFXPlayerSidebarView
import org.aas.sbtanks.entities.repository.context.EntityRepositoryContextInitializer

import scalafx.stage.Stage
import scalafx.scene.layout.Pane
import scalafx.scene.layout.BorderPane
import scalafx.scene.layout.Priority
import scalafx.scene.layout.VBox
import scalafx.scene.Node
import scalafx.geometry.Insets
import scalafx.geometry.Pos
import scalafx.scene.layout.StackPane

/**
  * Object containing context initializers that use ScalaFX
  */
object JFXEntityRepositoryContextInitializer extends scalafx.Includes:
    abstract class JFXInitializer[VSlotKey] extends EntityRepositoryContextInitializer[Stage, VSlotKey, Pane]

    /**
      * Creates an initializer for a game level context
      *
      * @param levelKey
      * @param uiKey
      * @return
      */
    def ofLevel[VSlotKey](levelKey: VSlotKey, uiKey: VSlotKey): JFXInitializer[VSlotKey] =
        new JFXInitializer[VSlotKey]:
            override def create(controller: Stage, currentSlots: ViewSlotsMap) =
                val levelContainer = Pane()
                val uiContainer = Pane()
                val scenePane = BorderPane(null, null, null, null, null)
                scenePane.center.set(levelContainer)
                scenePane.right.set(uiContainer)
                BorderPane.setAlignment(levelContainer, Pos.CENTER)
                BorderPane.setAlignment(uiContainer, Pos.CENTER)
                controller.scene.value.content.clear()
                controller.scene.value.content.add(scenePane)
                Map((levelKey, levelContainer), (uiKey, uiContainer))

    /**
      * Creates an initializer for a ui-only context
      *
      * @param uiKey
      */
    def ofView[VSlotKey](uiKey: VSlotKey): JFXInitializer[VSlotKey] =
        new JFXInitializer[VSlotKey]:
            override def create(controller: Stage, currentSlots: ViewSlotsMap) =
                val uiContainer = Pane()
                controller.scene.value.content.clear()
                controller.scene.value.content.add(uiContainer)
                Map((uiKey, uiContainer))