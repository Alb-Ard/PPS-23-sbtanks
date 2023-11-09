package org.aas.sbtanks.entities.repository

import scala.collection.mutable

object MockEntityMvRepositoryContainer:
    type MockModel = AnyRef
    type MockView = AnyRef
    type MockViewContainer = mutable.Buffer[MockView]

    type CompleteEntityRepository = EntityMvRepositoryContainer[MockModel, MockView]
        with EntityControllerRepository[MockModel, MockView, EntityRepositoryContext[MockViewContainer]]
        with EntityControllerReplacer[MockModel, MockView, EntityRepositoryContext[MockViewContainer]]
        with EntityViewAutoManager[MockView]
        with DestroyableEntityAutoManager[MockModel, MockView]
        with EntityRepositoryTagger[MockModel, MockView, Int]
        with EntityRepositoryContextAware[MockViewContainer, EntityRepositoryContext[MockViewContainer]]

    def apply(): CompleteEntityRepository = 
        given EntityRepositoryContext[MockViewContainer] = EntityRepositoryContext(mutable.Buffer.empty)
        new EntityMvRepositoryContainer[MockModel, MockView]()
                with EntityControllerRepository[MockModel, MockView, EntityRepositoryContext[MockViewContainer]]
                with EntityControllerReplacer[MockModel, MockView, EntityRepositoryContext[MockViewContainer]]
                with EntityViewAutoManager[MockView]
                with DestroyableEntityAutoManager[MockModel, MockView]
                with EntityRepositoryTagger[MockModel, MockView, Int]
                with EntityRepositoryContextAware:
                    override def addAutoManagedView(view: MockView) = 
                        summon[EntityRepositoryContext[MockViewContainer]].viewContainer :+ view
                        this
                
                    override def removeAutoManagedView(view: MockView) =
                        summon[EntityRepositoryContext[MockViewContainer]].viewContainer -= view
                        this
                        