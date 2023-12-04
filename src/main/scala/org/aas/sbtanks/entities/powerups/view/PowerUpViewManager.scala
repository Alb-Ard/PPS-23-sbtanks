package org.aas.sbtanks.entities.powerups.view

import scala.::

case class PowerUpViewManager(private val powerUpViews: Seq[PowerUpView], onViewRemoved: PowerUpView => Unit = _ => ()):

    def addView(view: PowerUpView): PowerUpViewManager =
        PowerUpViewManager(powerUpViews :+ view)

    def removeView(view: PowerUpView): PowerUpViewManager =
        PowerUpViewManager(powerUpViews.filterNot(_ == view))

    def removeLastAddedView(): PowerUpViewManager =
        powerUpViews.lastOption.fold(this) { lastView =>
            onViewRemoved(lastView)
            PowerUpViewManager(powerUpViews.init, onViewRemoved)
        }


