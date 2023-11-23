package org.aas.sbtanks.entities.repository.context

/**
  * Trait used to require implementers to specify a using with a repository context
  *
  * @param context The current context
  */
trait EntityRepositoryContextAware[VController, VSlotKey, VSlot](using context: EntityRepositoryContext[VController, VSlotKey, VSlot])