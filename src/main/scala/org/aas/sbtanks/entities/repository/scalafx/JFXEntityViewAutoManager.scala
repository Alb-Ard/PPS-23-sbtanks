package org.aas.sbtanks.entities.repository.scalafx

import org.aas.sbtanks.entities.repository.EntityRepositoryContextAware
import org.aas.sbtanks.entities.repository.EntityRepositoryContext
import org.aas.sbtanks.entities.repository.EntityViewAutoManager
import scalafx.application.Platform
import scalafx.stage.Stage
import scalafx.scene.Node
import scalafx.Includes
import org.aas.sbtanks.entities.tank.view.TankView

/**
  * Automatically adds views to the viewContainer in the given reporitory context
  *
  * @param context The context from which to take the viewContainer
  */
trait JFXEntityViewAutoManager(using context: EntityRepositoryContext[Stage]) extends EntityViewAutoManager[Node] with Includes:
    this: JFXEntityMvRepositoryContainer with EntityRepositoryContextAware[Stage, EntityRepositoryContext[Stage]] =>

    protected override def addAutoManagedView(view: Node) =
        Platform.runLater { 
            val insertIndex = view match
                case tv if JFXEntityViewAutoManager.BACK_LAYER_VIEW_TYPES
                        .filter(c => c.isAssignableFrom(view.getClass()))
                        .nonEmpty => 0
                case _ => Math.max(0, context.viewContainer.scene.value.content.length - 1)
            context.viewContainer.scene.value.content.insert(insertIndex, view)
        }
        this

    protected override def removeAutoManagedView(view: Node) =
        Platform.runLater { context.viewContainer.scene.value.content.remove(view) }
        this
    
object JFXEntityViewAutoManager:
    val BACK_LAYER_VIEW_TYPES = Seq(classOf[TankView])

