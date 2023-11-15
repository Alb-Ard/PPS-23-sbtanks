package org.aas.sbtanks.entities.repository

/**
  * Trait used to require implementers to specify a using with a repository context
  *
  * @param context
  */
trait EntityRepositoryContextAware[VController, VContainer](using context: EntityRepositoryContext[VController, VContainer])