package org.aas.sbtanks.entities.powerups

import org.aas.sbtanks.entities.powerups.effects.Timer.TimerPowerUp


/**
 * A trait representing a timeable power-up with a specified duration.
 *
 * @param duration The initial duration of the power-up.
 */
trait TimeablePowerUp(var duration: Double):
    private val originalDuration: Double = duration

    /**
     * Decreases the remaining duration of the power-up by the specified delta.
     *
     * @param delta The amount to decrease the duration by.
     * @return The current instance of TimeablePowerUp after decreasing the duration.
     */
    def decreaseDuration(delta: Double): this.type =
        duration -= delta
        this

    /**
     * Resets the duration of the power-up to its original value.
     *
     * @return The current instance of TimeablePowerUp after resetting the duration.
     */
    def resetDuration(): this.type =
        duration = originalDuration
        this

    /**
     * Checks if the power-up has expired (duration has reached or fallen below zero).
     *
     * @return true if the power-up has expired, otherwise false.
     */
    def isExpired: Boolean =
        duration <= 0
