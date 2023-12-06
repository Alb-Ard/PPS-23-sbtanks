package org.aas.sbtanks.player.controller.scalafx

import org.aas.sbtanks.Main.{INTERFACE_SCALE, windowSize}
import org.aas.sbtanks.behaviours.DamageableBehaviour
import org.aas.sbtanks.common.Steppable
import org.aas.sbtanks.entities.obstacles.LevelObstacle
import org.aas.sbtanks.entities.obstacles.LevelObstacle.Trees.playerBase
import org.aas.sbtanks.entities.repository.EntityMvRepositoryContainer
import org.aas.sbtanks.entities.repository.context.{EntityRepositoryContext, EntityRepositoryContextAware}
import org.aas.sbtanks.event.EventSource
import org.aas.sbtanks.lifecycle.{LevelSequencer, PointsManager}
import org.aas.sbtanks.lifecycle.scalafx.JFXGameBootstrapper
import org.aas.sbtanks.player.PlayerTank
import org.aas.sbtanks.resources.scalafx.JFXMediaPlayer
import scalafx.scene.Node
import scalafx.scene.layout.Pane
import scalafx.stage.Stage
import org.aas.sbtanks.lifecycle.PointsContainer

/**
 * this abstract class is a controller used whenever the player reaches a state of game over, either by losing
 * all player's lives or  when the player's base gets destroyed
 * @param context The current context
 * @param repository The repository containing all entities
 * @param levelSequencer The Sequencer used to go from one level to the next one
 * @param uiSlotKey
 * @tparam VSlotKey
 */
class JFXPlayerDeathController[VSlotKey](using context: EntityRepositoryContext[Stage, VSlotKey, Pane], points: PointsContainer)(repository: EntityMvRepositoryContainer[AnyRef, Node], levelSequencer: LevelSequencer[AnyRef, Node], uiSlotKey: VSlotKey)
    extends EntityRepositoryContextAware with Steppable:

    /**
      * Event invoked when the player dies
      */
    val playerDied = EventSource[Unit]

    private var playerTank = Option.empty[PlayerTank with DamageableBehaviour]
    private var baseObstacle = Option.empty[LevelObstacle with DamageableBehaviour]

    repository.modelViewAdded += { (m, _) => onModelCreated(m) }
    repository.modelViewReplaced += { a => onModelCreated(a.model) }
    repository.modelViewRemoved += { (m, _) => m match
        case p: PlayerTank with DamageableBehaviour if playerTank.contains(p) => playerTank = Option.empty
        case l: LevelObstacle with DamageableBehaviour if baseObstacle.contains(l) => baseObstacle = Option.empty
        case _ => ()
    }
    repository.entitiesOfModelType[PlayerTank with DamageableBehaviour].headOption.foreach(onModelCreated)

    override def step(delta: Double) = this

    private def gameover(source: Any) =
        playerDied(())

    /**
     * this def searches for any entity that is either a player tank or a player base and calls for gameover method.
     * @param m entity that can reach game over.
     */
    private def onModelCreated(m: AnyRef) = m match
        case p: PlayerTank with DamageableBehaviour =>
            playerTank.foreach(p => p.destroyed -= gameover)
            playerTank = Option(p)
            p.destroyed += gameover
        case l: LevelObstacle with DamageableBehaviour if (l.obstacleType == playerBase) =>
            baseObstacle.foreach(l => l.destroyed -= gameover)
            baseObstacle = Option(l)
            l.destroyed += gameover
        case _ => ()