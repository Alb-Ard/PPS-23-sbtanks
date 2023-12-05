package org.aas.sbtanks.entities.bullet.view

import org.aas.sbtanks.common.view.{DirectionableView, MoveableView}

/**
 * trait used for different implementations of bullet's view.
 */
trait BulletView extends MoveableView with DirectionableView

