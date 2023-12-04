package org.aas.sbtanks.entities.repository.scalafx

import org.aas.sbtanks.entities.repository.EntityControllerRepository
import org.aas.sbtanks.entities.repository.context.EntityRepositoryContext
import org.aas.sbtanks.entities.repository.context.EntityRepositoryContextAware

import scalafx.scene.Node
import scalafx.stage.Stage
import scalafx.scene.layout.Pane

trait JFXEntityControllerRepository[VSlotKey](using context: EntityRepositoryContext[Stage, VSlotKey, Pane]) 
    extends EntityControllerRepository[AnyRef, Node, EntityRepositoryContext[Stage, VSlotKey, Pane]]:
    this: JFXEntityMvRepositoryContainer with EntityRepositoryContextAware[Stage, VSlotKey, Pane] =>
