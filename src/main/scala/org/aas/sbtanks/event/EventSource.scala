package org.aas.sbtanks.event

class EventSource[A] extends EventBase:
    type EventCallback = A => Any

    private var listenersToRemove = Seq.empty[EventCallback]

    def once (callback: EventCallback) = 
        this += callback
        listenersToRemove = listenersToRemove :+ callback

    def apply(param: A): Unit =
        invoke(param)

    def invoke(param: A): Unit =
        foreachListener(l => l(param))
        listenersToRemove.foreach(l => this -= l)

