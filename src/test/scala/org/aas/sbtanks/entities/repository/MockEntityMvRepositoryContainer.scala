package org.aas.sbtanks.entities.repository

import scala.collection.mutable
import org.aas.sbtanks.common.ViewSlot
import org.aas.sbtanks.entities.repository.context.EntityRepositoryContext
import org.aas.sbtanks.entities.repository.context.EntityRepositoryContextInitializer
import org.aas.sbtanks.entities.repository.context.EntityRepositoryContextAware

object MockEntityMvRepositoryContainer:
    type MockModel = AnyRef
    type MockView = AnyRef
    type MockViewContainer = mutable.Buffer[MockView]
    type MockContext = EntityRepositoryContext[Any, ViewSlot, MockViewContainer]

    type CompleteEntityRepository = EntityMvRepositoryContainer[MockModel, MockView]
        with EntityControllerRepository[MockModel, MockView, MockContext]
        with EntityControllerReplacer[MockModel, MockView, MockContext]
        with EntityViewAutoManager[MockView]
        with DestroyableEntityAutoManager[MockModel, MockView]
        with EntityRepositoryTagger[MockModel, MockView, Int]
        with EntityRepositoryContextAware[Any, ViewSlot, MockViewContainer]

    def apply(): CompleteEntityRepository = 
        given EntityRepositoryContext[Any, ViewSlot, MockViewContainer] = EntityRepositoryContext(()).switch(new EntityRepositoryContextInitializer[Any, ViewSlot, MockViewContainer]:
            override def create(controller: Any, currentSlots: Map[ViewSlot, MockViewContainer]): ViewSlotsMap = 
                Map((ViewSlot.Game, mutable.Buffer.empty), (ViewSlot.Ui, mutable.Buffer.empty))
        )
        new EntityMvRepositoryContainer[MockModel, MockView]()
                with EntityControllerRepository[MockModel, MockView, MockContext]
                with EntityControllerReplacer[MockModel, MockView, MockContext]
                with EntityViewAutoManager[MockView]
                with DestroyableEntityAutoManager[MockModel, MockView]
                with EntityRepositoryTagger[MockModel, MockView, Int]
                with EntityRepositoryContextAware:
                    override def addAutoManagedView(view: MockView) = 
                        summon[MockContext].viewSlots.get(ViewSlot.Game).foreach(c => c :+ view)
                        this
                
                    override def removeAutoManagedView(view: MockView) =
                        summon[MockContext].viewSlots.get(ViewSlot.Game).foreach(c => c -= view)
                        this
                        