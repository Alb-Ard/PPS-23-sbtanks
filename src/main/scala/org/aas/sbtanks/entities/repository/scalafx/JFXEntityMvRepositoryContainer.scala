package org.aas.sbtanks.entities.repository.scalafx

import org.aas.sbtanks.entities.repository.EntityMvRepositoryContainer
import org.aas.sbtanks.entities.repository.EntityRepositoryContext
import scalafx.scene.Node
import scalafx.stage.Stage

case class JFXEntityMvRepositoryContainer() extends EntityMvRepositoryContainer[AnyRef, Node]