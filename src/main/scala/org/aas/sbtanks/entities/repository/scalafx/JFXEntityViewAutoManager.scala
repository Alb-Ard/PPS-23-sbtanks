package org.aas.sbtanks.entities.repository.scalafx

import org.aas.sbtanks.entities.repository.context.EntityRepositoryContextAware
import org.aas.sbtanks.entities.repository.context.EntityRepositoryContext
import org.aas.sbtanks.entities.repository.EntityViewAutoManager
import scalafx.application.Platform
import scalafx.stage.Stage
import scalafx.scene.Node
import scalafx.Includes
import org.aas.sbtanks.entities.tank.view.TankView
import scalafx.scene.layout.Pane

/**
  * Automatically adds views to the viewContainer in the given reporitory context
  *
  * @param context The context from which to take the viewContainer
  */
trait JFXEntityViewAutoManager[VSlotKey](using context: EntityRepositoryContext[Stage, VSlotKey, Pane])(gameKey: VSlotKey) extends EntityViewAutoManager[Node] with Includes:
    this: JFXEntityMvRepositoryContainer with EntityRepositoryContextAware[Stage, VSlotKey, Pane] =>

    protected override def addAutoManagedView(view: Node) =
        context.viewSlots.get(gameKey) match
            case None => ()
            case Some(c) =>        
                Platform.runLater { 
                    val insertIndex = view match
                        case tv if JFXEntityViewAutoManager.BACK_LAYER_VIEW_TYPES
                                .filter(c => c.isAssignableFrom(view.getClass()))
                                .nonEmpty => 0
                        case _ => Math.max(0, c.children.size - 1)
                    c.children.insert(insertIndex, view)
                }
        this

    protected override def removeAutoManagedView(view: Node) =
        Platform.runLater { context.viewSlots.get(gameKey).foreach(c => c.children.remove(view)) }
        this
    
object JFXEntityViewAutoManager:
    val BACK_LAYER_VIEW_TYPES = Seq(classOf[TankView])

