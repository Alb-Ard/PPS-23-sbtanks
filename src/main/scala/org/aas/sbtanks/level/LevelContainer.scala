package org.aas.sbtanks.level

import org.aas.sbtanks.entities.repository.EntityMvRepositoryContainer
import org.aas.sbtanks.obstacles.LevelObstacle
import org.aas.sbtanks.obstacles.view.scalafx.JFXObstacleView
import org.aas.sbtanks.resources.scalafx.JFXImageLoader

final case class LevelContainer[M, V](entityRepository: EntityMvRepositoryContainer[M, V])
