package org.aas.sbtanks.entities.repository.scalafx

import org.aas.sbtanks.entities.repository.EntityControllerRepository
import scalafx.scene.Node
import org.aas.sbtanks.entities.repository.EntityRepositoryContext
import scalafx.stage.Stage
import org.aas.sbtanks.entities.repository.EntityRepositoryContextAware

trait JFXEntityControllerRepository(using context: EntityRepositoryContext[Stage]) extends EntityControllerRepository[AnyRef, Node, EntityRepositoryContext[Stage]]:
    this: JFXEntityMvRepositoryContainer with EntityRepositoryContextAware[Stage, EntityRepositoryContext[Stage]] =>
