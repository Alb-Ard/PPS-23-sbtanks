package org.aas.sbtanks.entities.repository.scalafx

import org.aas.sbtanks.entities.repository.EntityRepositoryContextAware
import scalafx.stage.Stage
import org.aas.sbtanks.entities.repository.EntityRepositoryContext
import scalafx.Includes
import scalafx.scene.Node
import org.aas.sbtanks.entities.repository.EntityViewAutoManager

/**
  * Automatically adds views to the viewContainer in the given reporitory context
  *
  * @param context The context from which to take the viewContainer
  */
trait JFXEntityViewAutoManager(using context: EntityRepositoryContext[Stage]) extends EntityViewAutoManager[Node] with Includes:
    this: JFXEntityMvRepositoryContainer with EntityRepositoryContextAware[Stage, EntityRepositoryContext[Stage]] =>

    override def addAutoManagedView(view: Node) =
        context.viewContainer.scene.value.content.add(view)
        this

    override def removeAutoManagedView(view: Node) =
        context.viewContainer.scene.value.content.remove(view)
        this
    

