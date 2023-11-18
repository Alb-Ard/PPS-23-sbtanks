package org.aas.sbtanks.enemies.ai.fsm


trait StateModifier[S[_], E]:
    def getState: S[E]
    def setState(s: E): S[Unit]

    def gets[A](f: E => A): S[A]

    def modify(f: E => E): S[Unit]
