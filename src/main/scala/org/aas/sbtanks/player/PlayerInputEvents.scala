package org.aas.sbtanks.player

import scala.collection.mutable

trait PlayerInputEvents:
    val movementInputListeners = mutable.ListBuffer[(amountX: Double, amountY: Double) => Any]()

    protected def invokeListeners[A](listeners: mutable.ListBuffer[A])(invoker: A => Any) =
        listeners.foreach(invoker)
