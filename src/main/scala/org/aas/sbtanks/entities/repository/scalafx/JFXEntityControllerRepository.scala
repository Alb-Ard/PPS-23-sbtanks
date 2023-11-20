package org.aas.sbtanks.entities.repository.scalafx

import org.aas.sbtanks.entities.repository.EntityControllerRepository
import org.aas.sbtanks.entities.repository.EntityRepositoryContext
import org.aas.sbtanks.entities.repository.EntityRepositoryContextAware

import scalafx.scene.Node
import scalafx.stage.Stage
import scalafx.scene.layout.Pane

trait JFXEntityControllerRepository(using context: EntityRepositoryContext[Stage, Pane]) extends EntityControllerRepository[AnyRef, Node, EntityRepositoryContext[Stage, Pane]]:
    this: JFXEntityMvRepositoryContainer with EntityRepositoryContextAware[Stage, Pane] =>
