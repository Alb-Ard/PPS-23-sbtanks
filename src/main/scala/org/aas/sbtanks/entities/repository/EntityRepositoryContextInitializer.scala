package org.aas.sbtanks.entities.repository

import org.aas.sbtanks.event.EventSource

trait EntityRepositoryContextInitializer[VController, VContainer]:
    def createContainer(controller: VController, oldUiContainer: Option[VContainer], oldViewContainer: Option[VContainer]): (Option[VContainer], Option[VContainer])
