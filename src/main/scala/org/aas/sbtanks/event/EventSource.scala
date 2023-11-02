package org.aas.sbtanks.event

import scala.ref.WeakReference

class EventSource[A]:
    type EventCallback = A => Any

    private var listeners = List.empty[EventCallback]

    def += (callback: EventCallback) = 
        listeners = callback :: listeners

    def -= (callback: EventCallback) = 
        listeners = listeners.filterNot(callback.equals)

    def apply(param: A): Unit =
        invoke(param)

    def invoke(param: A): Unit =
        for c <- listeners do c(param)