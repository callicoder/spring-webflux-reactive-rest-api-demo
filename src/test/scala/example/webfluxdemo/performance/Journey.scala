package com.example.webfluxdemo.performance

import io.gatling.core.Predef._

import scala.concurrent.duration._

trait SimulationConfiguration extends Simulation with Scenarios {
  val constantRateTime: FiniteDuration
  val rampUpTime: FiniteDuration
  val rampDownTime: FiniteDuration
  val timeoutArEndOfTest: FiniteDuration = DurationInt(1).minutes

  lazy val load = 1D

  def perMinute(rate: Double): Double = rate / 60

}

class Journey extends SimulationConfiguration {
  val noLoad = 0.0001D
  override val constantRateTime: FiniteDuration = DurationInt(2).minutes
  override val rampUpTime: FiniteDuration = DurationInt(1).minutes
  override val rampDownTime: FiniteDuration = DurationInt(1).minutes

  setUp(getTweet.inject(
    rampUsersPerSec(noLoad) to load during(rampUpTime),
    constantUsersPerSec(load) during(constantRateTime),
    rampUsersPerSec(load) to (noLoad) during(rampDownTime)
  )).maxDuration(timeoutArEndOfTest)
    .assertions(global.failedRequests.count.lt(1))

}
