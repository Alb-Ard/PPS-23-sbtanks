package org.aas.sbtanks.enemies.ai




object State:
    import org.aas.sbtanks.enemies.ai.DirectionUtils.*

    /**
     * Defines a stateful computation with a result of type A
     * and a state transition function from S to (A, S).
     *
     * @param runAndReturn The state transition function, it applies a state change and a return of value.
     * @tparam S The type of the state.
     * @tparam A The type of the result.
     */
    case class State[S, A](runAndReturn: S => (A, S)):

        /**
         * Functor map operation for the State datatype.
         *
         * @param f The mapping function.
         * @tparam B The result type of the mapping function.
         * @return A new State datatype with the mapped result.
         */
        def map[B](f: A => B): State[S, B] =
            State(s0 =>
                val (a, s1) = runAndReturn(s0)
                (f(a), s1)
            )

        /**
         * Monad flatMap operation for the State datatype. it allows to create multiplicative State computations
         *
         * @param f The flatMap function.
         * @tparam B The result type of the flatMap function.
         * @return A new State monad resulting from sequencing computations.
         */
        def flatMap[B](f: A => State[S, B]): State[S, B] =
            State(s0 =>
                val (a, s1) = runAndReturn(s0)
                f(a).runAndReturn(s1)
            )

        /**
         * Monadic filter operation for the State datatype.
         *
         * @param p The predicate condition.
         * @return A new State monad with an Option result based on the predicate.
         */
        def withFilter(p: A => Boolean): State[S, Option[A]] =
            State(s0 =>
                val (a, s1) = runAndReturn(s0)
                if (p(a)) (Some(a), s1) else (None, s0)
            )





















