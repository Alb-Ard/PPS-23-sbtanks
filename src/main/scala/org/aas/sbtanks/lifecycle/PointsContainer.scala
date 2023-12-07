package org.aas.sbtanks.lifecycle

import org.aas.sbtanks.event.EventSource

/**
 * A trait to keep and update the current game score.
 */
trait PointsContainer:
    /**
     * An event that is invoked when the score is changed.
     */
    val amountChanged = EventSource[(Int)]

    private var points = 0

    /**
     * This method adds a certain amount to points to the total score.
     *
     * @param amount the amount of points that will be added.
     * @return this score.
     */
    def addAmount(amount: Int): this.type =
        points += amount
        amountChanged(points)
        this

    /**
     * This method resets the total score back to zero.
     *
     * @return this score.
     */
    def resetAmount(): this.type =
        points = 0
        amountChanged(0)
        this

    /**
     * This returns the total current game score.
     *
     * @return this score.
     */
    def amount = points


