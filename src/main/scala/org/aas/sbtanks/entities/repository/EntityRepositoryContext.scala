package org.aas.sbtanks.entities.repository

case class EntityRepositoryContext[VController, VContainer](val viewController: VController, val viewContainer: VContainer)