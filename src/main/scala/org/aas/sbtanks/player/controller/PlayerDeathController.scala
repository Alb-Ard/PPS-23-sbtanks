package org.aas.sbtanks.player.controller

import org.aas.sbtanks.player.{PlayerTank, PlayerTankBuilder}
class PlayerDeathController(player: PlayerTank):

    def respawn(): PlayerTank =
        val startingPosition = (0, 0)
        PlayerTankBuilder()
            .setPosition(startingPosition(0), startingPosition(1))
            .build()

