package org.aas.sbtanks.player.view.ui

/**
  * Interface for a view that shows the number of the level the player is currently playing
  */
trait PlayerLevelNumberView:
    /**
      * Sets the current leve number
      *
      * @param number The level number to set
      * @return This object
      */
    def setLevelNumber(number: Int): this.type