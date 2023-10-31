package org.aas.sbtanks.player

import org.scalatest.featurespec.AnyFeatureSpec
import org.scalatest.GivenWhenThen
import org.scalatest.matchers.should.Matchers
import org.aas.sbtanks.obstacles.LevelObstacle
import org.aas.sbtanks.physics.PhysicsWorld

class PlayerObstacleInteractionSpec extends AnyFeatureSpec with GivenWhenThen with Matchers:
    info("As a player")
    info("I want to see and interact with different obstacles in the game map")
    info("to make navigation more versatile.")

    Feature("Player obstacle collisions") {
        Scenario("A player colliding with a solid obstacle must not overlap with it") {
            Given("An empty physics world")
            PhysicsWorld.clearColliders()

            And("A player tank at position (0, 0)")
            val playerTank = PlayerTankBuilder().build()
            playerTank.positionX should be (0)
            playerTank.positionY should be (0)
            
            And("A tank controller")
            val playerController = MockJFXPlayerTankController(playerTank)

            And("A solid obstacle at position (0, 2)")
            val obstacle = LevelObstacle.SteelWall(0, 2)(0)
            obstacle.positionX should be (0)
            obstacle.positionY should be (2)
            
            When("The player moves towards the obstacle")
            playerController.simulateInput(MockJFXPlayerTankController.MOVE_DOWN_INPUT)

            And("Enough game steps happen")
            for step <- (0 until 100) do playerController.step(1)

            Then("The player should move towards the obstacle")
            playerTank.positionY should be > (0D)

            And("The player tank should not move past the obstacle")
            playerTank.positionY should not be > (1D)
        }

        Scenario("A player colliding with a non-solid obstacle should overlap it") {
            Given("An empty physics world")
            PhysicsWorld.clearColliders()

            And("A player tank at position (0, 0)")
            val playerTank = PlayerTankBuilder().build()
            playerTank.positionX should be (0)
            playerTank.positionY should be (0)

            And("A tank controller")
            val playerController = MockJFXPlayerTankController(playerTank)

            And("A non solid obstacle at position (0, 2)")
            val obstacle = LevelObstacle.Trees(0, 2)(0)
            obstacle.positionX should be (0)
            obstacle.positionY should be (2)

            When("The player moves towards the obstacle")
            playerController.simulateInput(MockJFXPlayerTankController.MOVE_DOWN_INPUT)

            And("Enough game steps happen")
            for step <- (0 until 300) do playerController.step(1)

            Then("The player tank should move past the obstacle")
            playerTank.positionY should be > (2D)
        }
    }