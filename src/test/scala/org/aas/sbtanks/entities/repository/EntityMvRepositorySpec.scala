package org.aas.sbtanks.entities.repository

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import org.aas.sbtanks.entities.repository.MockEntityMvRepositoryContainer.CompleteEntityRepository

class EntityMvRepositorySpec extends AnyFlatSpec with Matchers with EntityRepositoryTest:
    "An entity repository" should "be empty at creation" in withEntityRepository { repository =>
        repository.entityModelCount should be (0)
        repository.entityViewCount should be (0)
    }

    it should "contain one model with its view when it is added" in withEntityRepository { repository =>
        val entityModel = Object()
        val entityView = Object()
        repository.addModelView(entityModel, Option(entityView))
        repository.entityModelCount should be (1)
        repository.entityViewCount should be (1)
        val foundEntities = repository.entitiesOfMvTypes[Object, Object]
        foundEntities.size should be (1)
        foundEntities(0)(0) should be (entityModel)
        foundEntities(0)(1) should be (entityView)
    }

    it should "contain one model with no view when it is added without one" in withEntityRepository { repository =>
        val entityModel = Object()
        repository.addModelView(entityModel, Option.empty)
        repository.entityModelCount should be (1)
        repository.entityViewCount should be (0)
        val foundEntities = repository.entitiesOfModelType[Object]
        foundEntities.size should be (1)
        foundEntities(0)(0) should be (entityModel)
        foundEntities(0)(1) should be (Option.empty)
        repository.entitiesOfMvTypes[Object, Object].size should be (0)
        repository.entitiesOfViewType[Object].size should be (0)
    }

    it should "be empty when removing the last element" in withEntityRepository { repository =>
        val entityModel = Object()
        val entityView = Object()
        repository.addModelView(entityModel, Option(entityView))
        repository.entityModelCount should be (1)
        repository.entityViewCount should be (1)
        val foundEntities = repository.entitiesOfMvTypes[Object, Object]
        foundEntities.size should be (1)
        foundEntities(0)(0) should be (entityModel)
        foundEntities(0)(1) should be (entityView)
        repository.removeModelView(entityModel)
        repository.entityModelCount should be (0)
        repository.entityViewCount should be (0)
        repository.entitiesOfMvTypes[Object, Object].size should be (0)
    }