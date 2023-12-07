package org.aas.sbtanks.enemies.ai.fsm.movement

/**
 * enum policies for transitioning the direction in the context of AI movement state machine.
 */
enum DirectionMovePolicy:
    /**
     *  indicates a reset of the movement direction.
     */
    case Reset
    /**
     * indicates an attempt to change the movement direction.
     */
    case Try