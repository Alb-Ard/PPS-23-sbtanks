package org.aas.sbtanks.entities.repository.scalafx

import org.aas.sbtanks.entities.repository.EntityRepositoryContextAware
import scalafx.stage.Stage
import org.aas.sbtanks.entities.repository.EntityRepositoryContext
import scalafx.Includes

trait JFXEntityViewAutoManager(using context: EntityRepositoryContext[Stage]) extends Includes:
    this: JFXEntityMvRepositoryContainer with EntityRepositoryContextAware[Stage, EntityRepositoryContext[Stage]] =>

    modelViewAdded += { (_, v) => context.viewContainer.scene.value.content.add(v) }
    modelViewRemoved += { (_, v) => context.viewContainer.scene.value.content.remove(v) }

