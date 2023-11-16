package org.aas.sbtanks.player.view.ui

trait PlayerHealthView:
    def setRemainingHealth(amount: Int): this.type
