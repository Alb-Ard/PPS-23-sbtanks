package org.aas.sbtanks.entities.tank.repository

case class EntitySeqRepository[ItemType]() extends EntityRepository[EntitySeqRepository[ItemType]]:
    type B = ItemType

    private var items: Seq[ItemType] = Seq.empty

    def add(item: ItemType) =
        items = items :+ item
        this
    
    def ofType[A <: ItemType] = 
        items.map(i => i.asInstanceOf[A]).filter(m => m != null)
    
    def remove(item: ItemType) =
        items = items.filterNot(item.equals)
        this