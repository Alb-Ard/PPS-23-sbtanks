package org.aas.sbtanks.event

import scala.ref.WeakReference

class EventSource[A]:
    type EventCallback = A => Any

    private var listeners = List.empty[WeakReference[EventCallback]]

    def += (callback: EventCallback) = 
        listeners = WeakReference(callback) :: listeners

    def -= (callback: EventCallback) = 
        listeners = listeners.filterNot(c => c.get forall { oc => oc == callback })

    def apply(param: A): Unit =
        invoke(param)

    def invoke(param: A): Unit =
        listeners foreach { c => c.get foreach { oc => oc(param) } }