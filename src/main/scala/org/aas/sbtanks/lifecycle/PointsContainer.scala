package org.aas.sbtanks.lifecycle

import org.aas.sbtanks.event.EventSource

trait PointsContainer:
    val amountChanged = EventSource[(Int)]
    val highScoreChanged = EventSource[(Int)]

    private var points = 0
    private var highScoreValue = 0

    def addAmount(amount: Int): this.type =
        points += amount
        highScoreValue = points match
            case h if h > highScoreValue => 
                highScoreChanged(h)
                h
            case _ => highScoreValue
        amountChanged(points)
        this
    
    def resetAmount(): this.type =
        points = 0
        amountChanged(0)
        this

    def resetHighScore(): Unit =
        highScoreValue = 0
    
    def amount = points

    def highScore: Int = highScoreValue


