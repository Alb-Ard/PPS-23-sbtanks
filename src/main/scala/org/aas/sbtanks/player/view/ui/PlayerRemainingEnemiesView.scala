package org.aas.sbtanks.player.view.ui

/**
  * Interface for a view that shows the remaining enemies to defeat in a level
  */
trait PlayerRemainingEnemiesView:
    /**
      * Resets the view to the given enemy count
      *
      * @param count The enemy count
      */
    def setEnemyCount(count: Int): Unit

    /**
      * Removes one enemy from the view
      */
    def enemyDefeated(): Unit