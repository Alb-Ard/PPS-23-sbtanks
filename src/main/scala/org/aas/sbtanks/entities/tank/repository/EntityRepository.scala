package org.aas.sbtanks.entities.tank.repository

trait EntityRepository[A]:
    this: A =>
    type B

    def add(what: B): A
    def ofType[C <: B]: Seq[C]
    def remove(what: B): A
