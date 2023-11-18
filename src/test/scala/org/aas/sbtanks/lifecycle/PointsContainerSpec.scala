package org.aas.sbtanks.lifecycle

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import scala.util.Random

class PointsContainerSpec extends AnyFlatSpec with Matchers:
    class MockPointsContainer extends PointsContainer

    "A points container" should "start empty" in {
        val container = MockPointsContainer()
        container.amount should be (0)
    }

    it should "add a given amount of points when requested" in {
        val container = MockPointsContainer()
        val amountToAdd = Random.nextInt
        container.addAmount(amountToAdd)
        container.amount should be (amountToAdd)
    }

    it should "be empty when reset" in {
        val container = MockPointsContainer()
        val amountToAdd = Random.nextInt
        container.addAmount(amountToAdd)
        assume(container.amount == amountToAdd)
        container.resetAmount()
        container.amount should be (0)
    }

    it should "generate a changed event when adding points" in {
        var reportedAmount = 0
        val container = MockPointsContainer()
        container.amountChanged += { x => reportedAmount = x }
        val amountToAdd = Random.nextInt
        container.addAmount(amountToAdd)
        assume(container.amount == amountToAdd)
        reportedAmount should be (amountToAdd)
    }

    it should "generate a changed event when resetting" in {
        var reportedAmount = 0
        val container = MockPointsContainer()
        container.amountChanged += { x => reportedAmount = x }
        val amountToAdd = Random.nextInt
        container.addAmount(amountToAdd)
        assume(container.amount == amountToAdd)
        container.resetAmount()
        assume(container.amount == 0)
        reportedAmount should be (0)
    }