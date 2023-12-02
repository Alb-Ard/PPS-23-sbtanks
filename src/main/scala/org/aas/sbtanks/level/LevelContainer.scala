package org.aas.sbtanks.level

import org.aas.sbtanks.entities.repository.EntityMvRepositoryContainer
import org.aas.sbtanks.entities.obstacles.LevelObstacle
import org.aas.sbtanks.entities.obstacles.view.scalafx.JFXObstacleView
import org.aas.sbtanks.resources.scalafx.JFXImageLoader
import scala.reflect.ClassTag

/**
  * Object that wraps a entityRepository and gives functionality to manage it as a standalone level
  */
final class LevelContainer[M, V](using modelClassTag: ClassTag[M], viewClassTag: ClassTag[V])(entityRepository: EntityMvRepositoryContainer[M, V]):
    /**
      * Ends this level by clearing all entities.
      * 
      * @return All entities that were in the level
      */
    def end() =
        val allEntities = entityRepository.entities
        allEntities.foreach(mv => entityRepository.removeModelView(mv(0)))
        allEntities