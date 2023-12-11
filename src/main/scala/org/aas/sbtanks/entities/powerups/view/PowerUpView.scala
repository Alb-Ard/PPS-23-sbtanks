package org.aas.sbtanks.entities.powerups.view

import org.aas.sbtanks.common.view.MoveableView

/**
 * A trait representing the view of a power-up in a graphical user interface.
 * Extends the MoveableView trait to incorporate moveable behavior.
 */
trait PowerUpView extends MoveableView:
    def show(duration: Double): Unit

