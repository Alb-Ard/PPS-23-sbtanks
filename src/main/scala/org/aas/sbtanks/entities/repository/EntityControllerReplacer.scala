package org.aas.sbtanks.entities.repository

import org.aas.sbtanks.entities.repository.EntityMvRepositoryContainer
import org.aas.sbtanks.common.Steppable
import context.EntityRepositoryContext

/**
  * Adds the ability to register controller replacers, to automatically replace controllers when a view changes
  *
  * @param context The context that will be passed to the controller factories
  */
trait EntityControllerReplacer[Model, View, Context <: EntityRepositoryContext[?, ?, ?]](using context: Context):
    this: EntityMvRepositoryContainer[Model, View] with EntityControllerRepository[Model, View, Context] =>

    private case class ControllerReplacer[ReplacerParams](validModelPredicate: Model => Boolean, replacer: ReplacerParams => Controller)
    private type ControllerWithViewReplacer = ControllerReplacer[(Controller, Context, Model, View)]
    private type ControllerWithoutViewReplacer = ControllerReplacer[(Controller, Context, Model)]

    private var controllerWithViewReplacers = Seq.empty[ControllerWithViewReplacer]
    private var controllerWithoutViewReplacers = Seq.empty[ControllerWithoutViewReplacer]

    /**
      * Registers a controller replacer for models that have a view associated with them
      *
      * @param validPredicate The predicate to filter models on which this replacer will be applied
      * @param factory The replacer controller factory
      * @return This repository
      */
    def registerControllerReplacer[M <: Model, V <: View, C <: Controller](validPredicate: Model => Boolean, factory: (C, Context, M, V) => Controller): this.type =
        controllerWithViewReplacers = controllerWithViewReplacers :+ ControllerReplacer(validPredicate, (c, ctx, m, v) => factory(c.asInstanceOf[C], ctx, m.asInstanceOf[M], v.asInstanceOf[V]))
        this

    /**
      * Registers a controller replacer for models that don't have a view associated with them
      *
      * @param validPredicate The predicate to filter models on which this replacer will be applied
      * @param factory The replacer controller factory
      * @return This repository
      */
    def registerControllerReplacer[M <: Model, C <: Controller](validPredicate: Model => Boolean, factory: (C, Context, M) => Controller): this.type =
        controllerWithoutViewReplacers = controllerWithoutViewReplacers :+ ControllerReplacer(validPredicate, (c, ctx, m) => factory(c.asInstanceOf[C], ctx, m.asInstanceOf[M]))
        this
    
    /**
      * Gets the total amount of controller replacers registered in this repository
      *
      * @return The replacers count
      */
    def controllerReplacerCount = controllerWithViewReplacers.size + controllerWithoutViewReplacers.size

    modelViewReplaced += { p =>
        editControllers((model, controller) => {
            model match
                case Some(m) if m == p.model => 
                    p.newView match
                        case None => controllerWithoutViewReplacers.find(_.validModelPredicate(m)) match
                            case Some(r) => r.replacer(controller, context, m)
                            case _ => controller
                        case Some(v) => controllerWithViewReplacers.find(_.validModelPredicate(m)) match
                            case Some(r) => r.replacer(controller, context, m, v)
                            case _ => controller
                case _ => controller
        })
    }