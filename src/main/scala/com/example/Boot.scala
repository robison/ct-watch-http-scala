package com.example

import akka.actor.{ActorSystem, Props}
import akka.io.IO
import spray.can.Http

object Boot extends App {

  val system = ActorSystem("on-spray-can")
  
  
  val repo = new LogServerRepository
  val myService = new MyService(system, repo)
  
  val service = system.actorOf(Props(new MyServiceActor(myService)), "demo-service")

  IO(Http)(system) ! Http.Bind(service, interface = "localhost", port = 8088)
}