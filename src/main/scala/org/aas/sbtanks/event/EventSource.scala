package org.aas.sbtanks.event

class EventSource[A] extends EventBase:
    type EventCallback = A => Any

    def apply(param: A): Unit =
        invoke(param)

    def invoke(param: A): Unit =
        foreachListener(l => l(param))

