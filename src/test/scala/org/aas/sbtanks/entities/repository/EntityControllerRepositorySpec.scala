package org.aas.sbtanks.entities.repository

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.aas.sbtanks.common.Steppable

class EntityControllerRepositorySpec extends AnyFlatSpec with Matchers with EntityRepositoryTest:
    "An entity controller repository" should "be empty at creation" in withEntityRepository { repository =>
        repository.controllerCount should be (0)
        repository.controllerFactoryCount should be (0)
    }

    it should "be able to register a controller factory for a model with a view" in withEntityRepository { repository =>
        repository.registerControllerFactory(m => true, (c, m, v) => new Object() with Steppable {
            override def step(delta: Double) = this
        })
        repository.controllerFactoryCount should be (1)
    }

    it should "be able to register a controller factory for a model without a view" in withEntityRepository { repository =>
        repository.registerControllerFactory(m => true, (c, m) => new Object() with Steppable {
            override def step(delta: Double) = this
        })
        repository.controllerFactoryCount should be (1)
    }

    it should "be able to register multiple controller factories of different types" in withEntityRepository { repository =>
        repository.registerControllerFactory(m => true, (c, m, v) => new Object() with Steppable {
            override def step(delta: Double) = this
        })
        repository.registerControllerFactory(m => true, (c, m, v) => new Object() with Steppable {
            override def step(delta: Double) = this
        })
        repository.registerControllerFactory(m => true, (c, m) => new Object() with Steppable {
            override def step(delta: Double) = this
        })        
        repository.registerControllerFactory(m => true, (c, m) => new Object() with Steppable {
            override def step(delta: Double) = this
        })
        repository.controllerFactoryCount should be (4)
    }

    it should "create a controller when adding a model without a view that satisfies a factory predicate" in withEntityRepository { repository =>
        val model = Object()
        repository.registerControllerFactory(m => m.equals(model), (c, m) => new Object() with Steppable {
            m should be (model)

            override def step(delta: Double) = this
        })
        repository.controllerFactoryCount should be (1)
        repository.addModelView(model, Option.empty)
        repository.controllerCount should be (1)
        repository.addModelView(Object(), Option.empty)
        repository.controllerCount should be (1)
    }

    it should "create a controller when adding a model with a view that satisfies a factory predicate" in withEntityRepository { repository =>
        val model = Object()
        val view = Object()
        repository.registerControllerFactory(m => m.equals(model), (c, m, v) => new Object() with Steppable {
            m should be (model)
            v should be (view)

            override def step(delta: Double) = this
        })
        repository.controllerFactoryCount should be (1)
        repository.addModelView(model, Option(view))
        repository.controllerCount should be (1)
        repository.addModelView(Object(), Option(view))
        repository.controllerCount should be (1)
    }