package org.aas.sbtanks.entities.tank.repository

trait EntityModelRepository extends EntityRepository[EntityModelRepository]:
    type B = AnyRef

    private var models: Seq[AnyRef] = Seq.empty

    def add(model: AnyRef) =
        models = models :+ model
        this
    
    def ofType[A <: AnyRef] = 
        models.map(m => m.asInstanceOf[A]).filter(m => m != null)
    
    def remove(model: AnyRef) =
        models = models.filterNot(model.equals)
        this