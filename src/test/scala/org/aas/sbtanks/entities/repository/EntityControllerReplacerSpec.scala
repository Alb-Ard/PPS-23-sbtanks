package org.aas.sbtanks.entities.repository

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.aas.sbtanks.common.Steppable

class EntityControllerReplacerSpec extends AnyFlatSpec with Matchers with EntityRepositoryTest:
    "An entity controller replacer" should "be empty at creation" in withEntityRepository { repository =>
        repository.controllerReplacerCount should be (0)
    }

    it should "be able to register a controller replacer for a model with a view" in withEntityRepository { repository =>
        repository.registerControllerReplacer(m => true, (c, ctx, m, v) => new Object() with Steppable {
            override def step(delta: Double) = this
        })
        repository.controllerReplacerCount should be (1)
    }

    it should "be able to register a controller replacer for a model without a view" in withEntityRepository { repository =>
        repository.registerControllerReplacer(m => true, (c, ctx, m) => new Object() with Steppable {
            override def step(delta: Double) = this
        })
        repository.controllerReplacerCount should be (1)
    }

    it should "be able to register multiple controller replacers of different types" in withEntityRepository { repository =>
        repository.registerControllerReplacer(m => true, (c, ctx, m, v) => new Object() with Steppable {
            override def step(delta: Double) = this
        })
        repository.registerControllerReplacer(m => true, (c, ctx, m, v) => new Object() with Steppable {
            override def step(delta: Double) = this
        })
        repository.registerControllerReplacer(m => true, (c, ctx, m) => new Object() with Steppable {
            override def step(delta: Double) = this
        })        
        repository.registerControllerReplacer(m => true, (c, ctx, m) => new Object() with Steppable {
            override def step(delta: Double) = this
        })
        repository.controllerReplacerCount should be (4)
    }

    it should "replace an existing controller when replacing a model's view with a new one that satisfies a replacer predicate" in withEntityRepository { repository =>
        val model = Object()
        repository.registerControllerFactory(model.equals, (c, m) => new Object() with Steppable {
            m should be (model)

            override def step(delta: Double) = this
        })
        var wasControllerReplaced = false
        val newView = Object()
        repository.registerControllerReplacer(model.equals, (c, ctx, m, v) => new Object() with Steppable {
            m should be (model)
            v should be (newView)
            wasControllerReplaced = true

            override def step(delta: Double) = this
        })
        repository.addModelView(model, Option.empty)
        repository.controllerCount should be (1)
        repository.replaceView(model, Option(newView))
        repository.controllerCount should be (1)
        wasControllerReplaced should be (true)
    }

    it should "replace an existing controller when replacing a model's view with an empty one that satisfies a replacer predicate" in withEntityRepository { repository =>
        val model = Object()
        repository.registerControllerFactory(model.equals, (c, m) => new Object() with Steppable {
            m should be (model)

            override def step(delta: Double) = this
        })
        var wasControllerReplaced = false
        repository.registerControllerReplacer(model.equals, (c, ctx, m) => new Object() with Steppable {
            m should be (model)
            wasControllerReplaced = true

            override def step(delta: Double) = this
        })
        repository.addModelView(model, Option.empty)
        repository.controllerCount should be (1)
        repository.replaceView(model, Option.empty)
        repository.controllerCount should be (1)
        wasControllerReplaced should be (true)
    }