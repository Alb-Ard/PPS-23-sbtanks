package org.aas.sbtanks.player

import org.scalatest.featurespec.AnyFeatureSpec
import org.scalatest.GivenWhenThen
import org.scalatest.matchers.should.Matchers
import org.aas.sbtanks.behaviours.ConstrainedMovementBehaviour
import org.aas.sbtanks.behaviours.SteppedMovementDirectionBehaviour
import org.aas.sbtanks.behaviours.CollisionBehaviour
import org.aas.sbtanks.behaviours.PositionBehaviour
import org.aas.sbtanks.physics.CollisionLayer
import org.aas.sbtanks.obstacles.LevelObstacle

class PlayerObstacleInteractionSpec extends AnyFeatureSpec with GivenWhenThen with Matchers:
    info("As a player")
    info("I want to see and interact with different obstacles in the game map")
    info("to make navigation more versatile.")

    Feature("Player obstacle collisions") {
        Scenario("A player colliding with a solid obstacle must not overlap with it") {
            Given("A player tank")
            val playerTank = new Object() 
                with PositionBehaviour
                with ConstrainedMovementBehaviour 
                with SteppedMovementDirectionBehaviour(16)
                with CollisionBehaviour(16, 16, CollisionLayer.TanksLayer, Seq(CollisionLayer.WallsLayer))
            
            And("A solid obstacle at some distance from the player")
            val obstacle = LevelObstacle.BrickWall(0, 32)
            
            When("The player moves towards the obstacle")
            playerTank.moveTowards(0, 1)

            And("Enough game steps happen")
            for step <- (0 until 100) do playerTank.step(0.1)

            Then("The player should move towards the obstacle")
            playerTank.positionY should be > (16D)

            And("The player tank should not move past the obstacle")
            playerTank.positionY should not be > (32D)
        }

        Scenario("A player colliding with a non-solid obstacle should overlap it") {
            Given("A player tank")
            val playerTank = new Object() 
                with PositionBehaviour
                with ConstrainedMovementBehaviour 
                with SteppedMovementDirectionBehaviour(16)
                with CollisionBehaviour(16, 16, CollisionLayer.TanksLayer, Seq(CollisionLayer.WallsLayer))

            And("A non solid obstacle at some distance from the player")
            val obstacle = LevelObstacle.Trees(0, 32)

            When("The player moves towards the obstacle")
            playerTank.moveTowards(0, 1)

            And("Enough game steps happen")
            for step <- (0 until 100) do playerTank.step(0.1)

            Then("The player tank should move past the obstacle")
            playerTank.positionY should be > (32D)
        }
    }