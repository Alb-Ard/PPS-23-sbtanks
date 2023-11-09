package org.aas.sbtanks.entities.repository

/**
  * Adds the functionality to tag a model with one or more tags of a given type
  */
trait EntityRepositoryTagger[M, V, T]:
    this: EntityMvRepositoryContainer[M, V] =>

    modelViewRemoved += { (m, _) => clearTags(m) }

    var tags = Seq.empty[(T, M)]

    def addTag(model: M, tag: T): this.type =
        tags = tags :+ ((tag, model))
        this

    def removeTag(model: M, tag: T): this.type =
        tags = tags.filterNot((t, m) => model == m && tag == t)
        this

    def clearTags(model: M) =
        getTags(model).foldLeft(this)((tg, t) => tg.removeTag(model, t))

    def getByTag(tag: T) =
        tags.filter(_(0) == tag).map(_(1))

    def getTags(model: M) = 
        tags.filter(_(1) == model).map(_(0))
    
object EntityRepositoryTagger:
    extension [M, V, T](tagger: EntityRepositoryTagger[M, V, T])
        def hasTag(model: M, tag: T) =
            tagger.getByTag(tag).contains(model)