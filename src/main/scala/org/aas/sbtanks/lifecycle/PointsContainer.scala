package org.aas.sbtanks.lifecycle

import org.aas.sbtanks.event.EventSource

trait PointsContainer:
    val amountChanged = EventSource[(Int)]

    private var points = 0
    private var highestScore = 0

    def addAmount(amount: Int): this.type =
        points += amount
        if(highestScore < points) highestScore = points
        amountChanged(points)
        this
    
    def resetAmount(): this.type =
        points = 0
        amountChanged(0)
        this
    
    def amount = points

    def bestScore = highestScore


