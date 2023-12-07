package org.aas.sbtanks.lifecycle

import scala.collection.immutable.Queue
import org.aas.sbtanks.level.LevelContainer
import org.aas.sbtanks.level.LevelFactory
import org.aas.sbtanks.player.PlayerTank
import scala.reflect.ClassTag
import org.aas.sbtanks.event.EventSource

/**
 * This class's main function is to load the proper sequence of the game'levels.
 *
 * @param modelClassTag
 * @param viewClassTag
 * @param levels
 * @param levelFactory
 * @param entityRepository
 * @tparam M
 * @tparam V
 */
class LevelSequencer[M >: PlayerTank, V](using modelClassTag: ClassTag[M], viewClassTag: ClassTag[V])(levels: Seq[(String, Int, String)], levelFactory: LevelFactory[M, V], entityRepository: LevelFactory[M, V]#LevelEntityRepository):
    val levelChanged = EventSource[(LevelContainer[M, V], String)]

    private var remainingLevelsQueue = Queue.from(levels)
    private var currentLevel = Option.empty[LevelContainer[M, V]]

    /**
     * returns the number of levels completed.
     *
     * @return Number of completed levels.
     */
    def completedLevelCount = levels.size - remainingLevelsQueue.size - 1

    /**
     * starts the current level.
     *
     * @return the current level.
     */
    def start(): this.type = currentLevel match
        case None => advanceToNextLevel()
        case Some(_) => this

    /**
     * declares the current level as complete and calls another method to move to next level.
     *
     * @return the current level.
     */
    def completeLevel(): this.type = currentLevel match
        case None => this
        case Some(_) => advanceToNextLevel()

    /**
     * resets the current level.
     *
     * @return this current level.
     */
    def reset(): this.type = 
        endCurrentLevel()
        currentLevel = Option.empty
        remainingLevelsQueue = Queue.from(levels)
        this

    private def advanceToNextLevel(): this.type =
        val player = endCurrentLevel()
        remainingLevelsQueue.dequeueOption match
            case None => 
                remainingLevelsQueue = Queue.empty
                currentLevel = Option.empty
            case Some((level, newQueue)) =>
                remainingLevelsQueue = newQueue
                currentLevel = Option(levelFactory.createFromString(level(0), level(1), entityRepository))
                copyPlayerHealth(player)
                levelChanged(currentLevel.get, level(2))
        this
    
    private def endCurrentLevel() =
        currentLevel.map(l => l.end())
            .getOrElse(Seq.empty)
            .filter(mv => mv(0).isInstanceOf[PlayerTank])
            .headOption
            .map(mv => mv(0).asInstanceOf[PlayerTank])
        
    private def copyPlayerHealth(oldPlayer: Option[PlayerTank]) =
        oldPlayer.foreach(p => entityRepository
            .entitiesOfModelType[PlayerTank]
            .headOption
            .foreach(mv => mv(0)
                .updateTankData(mv(0).tankData.updateHealth(_=> p.tankData.health))))