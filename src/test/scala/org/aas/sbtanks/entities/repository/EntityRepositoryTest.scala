package org.aas.sbtanks.entities.repository

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.aas.sbtanks.entities.repository.MockEntityMvRepositoryContainer.CompleteEntityRepository

trait EntityRepositoryTest:
    def withEntityRepository(test: CompleteEntityRepository => Any) =
        test(MockEntityMvRepositoryContainer())