package org.aas.sbtanks.event.scalafx

import org.aas.sbtanks.event.EventSource

import scala.collection.mutable
import scalafx.beans.property.{DoubleProperty, IntegerProperty, StringProperty}
import org.aas.sbtanks.event.EventBase
import scalafx.beans.Observable

/**
  * Provides extension methods to bridge native Event Sources with ScalaFx reactive properties
  */
object JFXEventSource:
    /**
      * Keeps a cached reference to previous created properties. Uses a weak map since this should not count
      * to the total references for both events and properties
      */
    private val PROPERTIES = mutable.WeakHashMap.empty[EventBase, Observable]

    extension (event: EventSource[Int])
        /**
          * Gets a property binded on this event
          *
          * @return The property
          */
        def toIntProperty() =
            val property = getProperty(event, IntegerProperty(0))
            event += property.update
            property

    extension (event: EventSource[Double])
        /**
          * Gets a property binded on this event
          *
          * @return The property
          */
        def toDoubleProperty() =
            val property = getProperty(event, DoubleProperty(0))
            event += property.update
            property

    extension (event: EventSource[String])
        def toStringProperty() =
            val property = getProperty(event, StringProperty(""))
            event += property.update
            property
    
    private def getProperty[P <: Observable](event: EventBase, defaultProperty: => P) =
        PROPERTIES.getOrElseUpdate(event, defaultProperty).asInstanceOf[P]