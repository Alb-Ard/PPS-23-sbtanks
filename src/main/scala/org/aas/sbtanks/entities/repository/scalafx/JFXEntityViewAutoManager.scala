package org.aas.sbtanks.entities.repository.scalafx

import org.aas.sbtanks.entities.repository.EntityRepositoryContextAware
import org.aas.sbtanks.entities.repository.EntityRepositoryContext
import org.aas.sbtanks.entities.repository.EntityViewAutoManager
import scalafx.application.Platform
import scalafx.stage.Stage
import scalafx.scene.Node
import scalafx.Includes

/**
  * Automatically adds views to the viewContainer in the given reporitory context
  *
  * @param context The context from which to take the viewContainer
  */
trait JFXEntityViewAutoManager(using context: EntityRepositoryContext[Stage]) extends EntityViewAutoManager[Node] with Includes:
    this: JFXEntityMvRepositoryContainer with EntityRepositoryContextAware[Stage, EntityRepositoryContext[Stage]] =>

    override def addAutoManagedView(view: Node) =
        Platform.runLater { context.viewContainer.scene.value.content.add(view) }
        this

    override def removeAutoManagedView(view: Node) =
        Platform.runLater { context.viewContainer.scene.value.content.remove(view) }
        this
    

