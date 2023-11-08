package org.aas.sbtanks.event

import scala.ref.WeakReference

trait EventBase:
    type EventCallback

    private var listeners = List.empty[EventCallback]

    def += (callback: EventCallback) = 
        listeners = callback :: listeners

    def -= (callback: EventCallback) = 
        listeners = listeners.filterNot(callback.equals)
    
    protected def foreachListener(consumer: EventCallback => Any) =
        for c <- listeners do consumer(c)