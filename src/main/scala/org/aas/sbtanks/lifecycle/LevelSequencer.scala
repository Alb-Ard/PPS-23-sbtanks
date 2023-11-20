package org.aas.sbtanks.lifecycle

import scala.collection.immutable.Queue
import org.aas.sbtanks.level.LevelContainer
import org.aas.sbtanks.level.LevelFactory
import org.aas.sbtanks.player.PlayerTank
import scala.reflect.ClassTag
import org.aas.sbtanks.event.EventSource

class LevelSequencer[M >: PlayerTank, V](using modelClassTag: ClassTag[M], viewClassTag: ClassTag[V])(levels: Seq[(String, Int, Int)], levelFactory: LevelFactory[M, V], entityRepository: LevelFactory[M, V]#LevelEntityRepository):
    val levelChanged = EventSource[(LevelContainer[M, V], Int)]

    private var queue = Queue.from(levels)

    private var currentLevel = Option.empty[LevelContainer[M, V]]

    def start(): this.type = currentLevel match
        case None => advanceToNextLevel()
        case Some(_) => this

    def completeLevel(): this.type = currentLevel match
        case None => this
        case Some(_) => advanceToNextLevel()

    private def advanceToNextLevel(): this.type =
        val player = endCurrentLevel()
        queue.dequeueOption match
            case None => 
                queue = Queue.empty
                currentLevel = Option.empty
            case Some(((levelString, levelSize, enemyCount), newQueue)) =>
                queue = newQueue
                currentLevel = Option(levelFactory.createFromString(levelString, levelSize, entityRepository))
                copyPlayerHealth(player)
                levelChanged(currentLevel.get, enemyCount)
        this
    
    def endCurrentLevel() =
        currentLevel.map(l => l.end())
            .getOrElse(Seq.empty)
            .filter(mv => mv(0).isInstanceOf[PlayerTank])
            .headOption
            .map(mv => mv(0).asInstanceOf[PlayerTank])
        
    def copyPlayerHealth(oldPlayer: Option[PlayerTank]) =
        oldPlayer.foreach(p => entityRepository
            .entitiesOfModelType[PlayerTank]
            .headOption
            .foreach(mv => mv(0)
                .updateTankData(mv(0).tankData.updateHealth(_=> p.tankData.health))))