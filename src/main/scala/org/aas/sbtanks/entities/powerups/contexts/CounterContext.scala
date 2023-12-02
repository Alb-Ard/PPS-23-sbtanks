package org.aas.sbtanks.entities.powerups.contexts

class CounterContext(var counter: Int):

    def +=(i: Int): Unit =
        counter +=  i

    def -=(i: Int): Unit =
        counter -= i

    def getCounter: Int = counter


object CounterContext extends App:
    def apply(counter: Int): CounterContext = new CounterContext(counter)

    def unapply(counterContext: CounterContext): Option[Int] = Some(counterContext.getCounter)







