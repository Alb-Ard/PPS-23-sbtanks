package org.aas.sbtanks.entities.repository.context

import org.aas.sbtanks.event.EventSource

/**
  * A context that contains information to manage the game view 
  *
  * @param viewController The main view controller
  */
case class EntityRepositoryContext[VController, VSlotKey, VSlot](val viewController: VController):
  type ViewSlotsMap = Map[VSlotKey, VSlot]

    /**
      * Event emitted when a context switch happens.
      * Parameters are the old view slots and the new ones
      */
    val changed = EventSource[(ViewSlotsMap, ViewSlotsMap)]

    private var viewSlotsMap = Map.empty[VSlotKey, VSlot]

    /**
      * The current view slots
      *
      * @return An option with the current container
      */
    def viewSlots = viewSlotsMap

    /**
      * Switches the context using a specified initializer
      *
      * @param initializer The context initializer to use
      * @return This object
      */
    def switch(initializer: EntityRepositoryContextInitializer[VController, VSlotKey, VSlot]): this.type =
        val oldSlots = viewSlots
        val newSlots = initializer.create(viewController, viewSlots)
        viewSlotsMap = newSlots
        //changed(ChangedEventArgs(oldUiContainer, oldViewContainer, newUiContainer, newViewContainer))
        this