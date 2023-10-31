package org.aas.sbtanks.entities.tank.repository.scalafx

import org.aas.sbtanks.entities.tank.repository.EntityMvcRepositoryContainer
import scalafx.scene.Node

case class JFXEntityMvcRepositoryContainer() extends EntityMvcRepositoryContainer:
    type View = Node