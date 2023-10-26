package org.aas.sbtanks.entities.powerups.view

import scalafx.util.Duration

trait IntermittentVisible[E]:
    this: E =>
    def activate(): Unit







