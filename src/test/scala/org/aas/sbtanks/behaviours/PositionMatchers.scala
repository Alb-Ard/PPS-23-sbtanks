package org.aas.sbtanks.behaviours

import org.scalatest.matchers.HavePropertyMatcher
import org.scalatest.matchers.HavePropertyMatchResult

trait PositionMatchers():
    def position(expectedValue: (Double, Double)) =
        new HavePropertyMatcher[PositionBehaviour, (Double, Double)]:
            override def apply(objectWithProperty: PositionBehaviour) = 
                HavePropertyMatchResult(
                    expectedValue(0) == objectWithProperty.positionX && expectedValue(1) == objectWithProperty.positionY,
                    "positionX and positionY",
                    expectedValue,
                    (objectWithProperty.positionX, objectWithProperty.positionY)
                )

    def positionX(expectedValue: Double) =
        new HavePropertyMatcher[PositionBehaviour, Double]:
            override def apply(objectWithProperty: PositionBehaviour) = 
                HavePropertyMatchResult(
                    expectedValue == objectWithProperty.positionX,
                    "positionX",
                    expectedValue,
                    objectWithProperty.positionX
                )

    def positionY(expectedValue: Double) =
        new HavePropertyMatcher[PositionBehaviour, Double]:
            override def apply(objectWithProperty: PositionBehaviour) = 
                HavePropertyMatchResult(
                    expectedValue == objectWithProperty.positionY,
                    "positionY",
                    expectedValue,
                    objectWithProperty.positionY
                )
