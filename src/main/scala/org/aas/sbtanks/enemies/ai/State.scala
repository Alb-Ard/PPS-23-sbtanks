package org.aas.sbtanks.enemies.ai




object State:
    import org.aas.sbtanks.enemies.ai.DirectionUtils.*

    trait Functor[F[_]]:
        def map[A, B](fa: F[A])(f: A => B): F[B]

    trait Monad[F[_]] extends Functor[F]:
        def pure[A](a: A): F[A]
        def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B]

        override def map[A, B](fa: F[A])(f: A => B): F[B] =
            flatMap(fa)(a => pure(f(a)))


    case class State[S, A](runAndTranslate: S => (A, S)):


        def map[B](f: A => B): State[S, B] =
            State(s0 =>
                val (a, s1) = runAndTranslate(s0)
                (f(a), s1)
            )

        def flatMap[B](f: A => State[S, B]): State[S, B] =
            State(s0 =>
                val (a, s1) = runAndTranslate(s0)
                f(a).runAndTranslate(s1)
            )


        def withFilter(p: A => Boolean): State[S, Option[A]] =
            State(s0 =>
                val (a, s1) = runAndTranslate(s0)
                if (p(a)) (Some(a), s1) else (None, s0)
            )





















