package org.aas.sbtanks.entities.repository

import org.aas.sbtanks.event.EventSource

/**
  * A context that contains information to manage the game view 
  *
  * @param viewController The main view controller
  */
case class EntityRepositoryContext[VController, VContainer](val viewController: VController):
    case class ChangedEventArgs(oldUiContainer: Option[VContainer], oldViewContainer: Option[VContainer], newUiContainer: Option[VContainer], newViewContainer: Option[VContainer])
    /**
      * Event emitted when a context switch happens.
      * Parameters are the old container and the new container
      */
    val changed = EventSource[ChangedEventArgs]

    private var currentViewContainer = Option.empty[VContainer]
    private var currentUiContainer = Option.empty[VContainer]

    /**
      * The current views container, if any is present
      *
      * @return An option with the current container
      */
    def viewContainer = currentViewContainer

    /**
      * The current ui container, if any is present
      *
      * @return An option with the current container
      */
    def uiContainer = currentUiContainer

    /**
      * Switches the context using a specified initializer
      *
      * @param initializer The context initializer to use
      * @return This object
      */
    def switch(initializer: EntityRepositoryContextInitializer[VController, VContainer]): this.type =
        val oldUiContainer = currentUiContainer
        val oldViewContainer = currentViewContainer
        val (newUiContainer, newViewContainer) = initializer.createContainer(viewController, oldUiContainer, oldViewContainer)
        currentUiContainer = newUiContainer
        currentViewContainer = newViewContainer
        changed(ChangedEventArgs(oldUiContainer, oldViewContainer, newUiContainer, newViewContainer))
        this