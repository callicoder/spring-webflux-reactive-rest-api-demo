package com.example.webfluxdemo.performance

import io.gatling.core.Predef._
import io.gatling.http.Predef._

trait Scenarios {
  val baseUrl: String = "http://localhost:8080"

  val getHeaders = Map("Content-Type" -> "application/json")

  val getTweet = scenario("get tweet")
    .exec(
      http("get tweet endpoint test : " + baseUrl)
        .get(baseUrl + "/tweets")
        .headers(getHeaders)
        .check(status.is(200))
        .check(responseTimeInMillis.lte(expected = 100))
    )

}
