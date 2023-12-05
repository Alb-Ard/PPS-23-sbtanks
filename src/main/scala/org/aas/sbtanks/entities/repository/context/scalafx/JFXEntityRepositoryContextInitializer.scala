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
import scalafx.scene.layout.Background

/**
  * Object containing context initializers that use ScalaFX
  */
object JFXEntityRepositoryContextInitializer extends scalafx.Includes:
    abstract class JFXInitializer[VSlotKey] extends EntityRepositoryContextInitializer[Stage, VSlotKey, Pane]

    /**
      * Creates an initializer for a game level context
      *
      * @param levelKey The slot key that will identify the game view container
      * @param uiKey The slot key that will identify the game ui container
      * @param overlayKey The slot key that will identify the overlay over the game container
      * @return
      */
    def ofLevel[VSlotKey](levelKey: VSlotKey, uiKey: VSlotKey, overlayKey: VSlotKey): JFXInitializer[VSlotKey] =
        new JFXInitializer[VSlotKey]:
            override def create(controller: Stage, currentSlots: ViewSlotsMap) =
                val levelContainer = new Pane():
                    background = Background.Empty
                    alignmentInParent = Pos.Center
                val uiContainer = new Pane():
                    background = Background.Empty
                    alignmentInParent = Pos.Center
                val scenePane = new BorderPane(null, null, null, null, null):
                    background = Background.Empty
                    alignmentInParent = Pos.Center
                val overlayPane = new StackPane:
                    background = Background.Empty
                    alignmentInParent = Pos.Center
                    alignment = Pos.Center
                val mainPane = new StackPane:
                    background = Background.Empty
                    alignment = Pos.Center
                scenePane.center.set(levelContainer)
                scenePane.right.set(uiContainer)
                mainPane.children.addAll(scenePane, overlayPane)
                controller.scene.value.root = mainPane
                Map((levelKey, levelContainer), (uiKey, uiContainer), (overlayKey, overlayPane))

    /**
      * Creates an initializer for a ui-only context
      *
      * @param uiKey The slot key that will identify the ui container
      */
    def ofView[VSlotKey](uiKey: VSlotKey): JFXInitializer[VSlotKey] =
        new JFXInitializer[VSlotKey]:
            override def create(controller: Stage, currentSlots: ViewSlotsMap) =
                val uiContainer = new Pane():
                    background = Background.Empty
                controller.scene.value.root = uiContainer
                Map((uiKey, uiContainer))