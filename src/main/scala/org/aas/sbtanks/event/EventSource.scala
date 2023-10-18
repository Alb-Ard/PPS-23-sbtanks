package org.aas.sbtanks.event

class EventSource[A]:
    type EventCallback = A => Any

    private var listeners = List.empty[EventCallback]

    def += (callback: EventCallback) = 
        listeners = callback :: listeners

    def -= (callback: EventCallback) = 
        listeners = listeners.filterNot(c => c != callback)

    def apply(param: A): Unit =
        invoke(param)

    def invoke(param: A): Unit =
        listeners foreach { p => p(param) }