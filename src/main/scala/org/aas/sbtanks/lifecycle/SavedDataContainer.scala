package org.aas.sbtanks.lifecycle

import org.aas.sbtanks.event.EventSource

trait SavedDataContainer:
    val highScoreChanged = EventSource[(Int)]
    val usernameChanged = EventSource[(String)]

    private var highScoreValue = 0
    private var usernameText = ""

    def increaseHighScore(amount: Int): this.type =
        highScoreValue = amount
        highScoreChanged(highScoreValue)
        this

    def setUsername(username: String): this.type =
        usernameText = username
        usernameChanged(usernameText)
        this

    def resetHighScore(): this.type =
        highScoreValue = 0
        highScoreChanged(0)
        this

    def highScore: Int = highScoreValue
    def username: String = usernameText
