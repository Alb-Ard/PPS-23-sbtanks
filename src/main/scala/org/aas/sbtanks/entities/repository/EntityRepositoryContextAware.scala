package org.aas.sbtanks.entities.repository

trait EntityRepositoryContextAware[VC, C <: EntityRepositoryContext[VC]](using context: C)