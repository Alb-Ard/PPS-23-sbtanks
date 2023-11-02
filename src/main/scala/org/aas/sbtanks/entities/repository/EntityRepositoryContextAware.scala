package org.aas.sbtanks.entities.repository

/**
  * Trait used to require implementers to specify a using with a repository context
  *
  * @param context
  */
trait EntityRepositoryContextAware[VC, C <: EntityRepositoryContext[VC]](using context: C)