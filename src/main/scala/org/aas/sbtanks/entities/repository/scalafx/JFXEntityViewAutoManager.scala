package org.aas.sbtanks.entities.repository.scalafx

import org.aas.sbtanks.entities.repository.EntityRepositoryContextAware
import scalafx.stage.Stage
import org.aas.sbtanks.entities.repository.EntityRepositoryContext
import scalafx.Includes

/**
  * Automatically adds views to the viewContainer in the given reporitory context
  *
  * @param context The context from which to take the viewContainer
  */
trait JFXEntityViewAutoManager(using context: EntityRepositoryContext[Stage]) extends Includes:
    this: JFXEntityMvRepositoryContainer with EntityRepositoryContextAware[Stage, EntityRepositoryContext[Stage]] =>

    modelViewAdded += { (_, v) => context.viewContainer.scene.value.content.add(v) }
    modelViewRemoved += { (_, v) => context.viewContainer.scene.value.content.remove(v) }

