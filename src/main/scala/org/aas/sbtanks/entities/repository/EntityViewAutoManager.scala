package org.aas.sbtanks.entities.repository

/**
  * Automatically adds views to the viewContainer in the given reporitory context
  *
  * @param context The context from which to take the viewContainer
  */
trait EntityViewAutoManager[V]:
    this: EntityMvRepositoryContainer[?, V] =>

    modelViewAdded += { (_, v) => v.foreach(addAutoManagedView) }
    modelViewReplaced += { p => 
        p.oldView.foreach(removeAutoManagedView)
        p.newView.foreach(addAutoManagedView)
    }
    modelViewRemoved += { (_, v) => v.foreach(removeAutoManagedView) }

    protected def addAutoManagedView(view: V): this.type

    protected def removeAutoManagedView(view: V): this.type
