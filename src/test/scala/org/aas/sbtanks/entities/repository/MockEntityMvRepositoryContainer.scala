package org.aas.sbtanks.entities.repository

import scala.collection.mutable

object MockEntityMvRepositoryContainer:
    type MockModel = AnyRef
    type MockView = AnyRef
    type MockViewContainer = mutable.Buffer[MockView]
    type MockContext = EntityRepositoryContext[Any, MockViewContainer]

    type CompleteEntityRepository = EntityMvRepositoryContainer[MockModel, MockView]
        with EntityControllerRepository[MockModel, MockView, MockContext]
        with EntityControllerReplacer[MockModel, MockView, MockContext]
        with EntityViewAutoManager[MockView]
        with DestroyableEntityAutoManager[MockModel, MockView]
        with EntityRepositoryTagger[MockModel, MockView, Int]
        with EntityRepositoryContextAware[Any, MockViewContainer]

    def apply(): CompleteEntityRepository = 
        given EntityRepositoryContext[Any, MockViewContainer] = EntityRepositoryContext((), mutable.Buffer.empty)
        new EntityMvRepositoryContainer[MockModel, MockView]()
                with EntityControllerRepository[MockModel, MockView, MockContext]
                with EntityControllerReplacer[MockModel, MockView, MockContext]
                with EntityViewAutoManager[MockView]
                with DestroyableEntityAutoManager[MockModel, MockView]
                with EntityRepositoryTagger[MockModel, MockView, Int]
                with EntityRepositoryContextAware:
                    override def addAutoManagedView(view: MockView) = 
                        summon[MockContext].viewContainer :+ view
                        this
                
                    override def removeAutoManagedView(view: MockView) =
                        summon[MockContext].viewContainer -= view
                        this
                        