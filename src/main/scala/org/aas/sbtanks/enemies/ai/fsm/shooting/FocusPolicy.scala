package org.aas.sbtanks.enemies.ai.fsm.shooting

/**
 * Enumeration representing the focus policies for the AI shooting state machine.
 */
enum FocusPolicy:
    /**
     * Indicates that the AI should focus on the target.
     */
    case Focus
    
    /**
     * Indicates that the AI should be in an idle state without focusing on a target.
     */
    case Idle