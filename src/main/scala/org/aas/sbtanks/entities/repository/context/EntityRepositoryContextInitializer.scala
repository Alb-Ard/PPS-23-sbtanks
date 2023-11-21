package org.aas.sbtanks.entities.repository.context

import org.aas.sbtanks.event.EventSource

trait EntityRepositoryContextInitializer[VController, VSlotKey, VSlot]:
    type ViewSlotsMap = Map[VSlotKey, VSlot]

    def create(controller: VController, currentSlots: ViewSlotsMap): ViewSlotsMap
