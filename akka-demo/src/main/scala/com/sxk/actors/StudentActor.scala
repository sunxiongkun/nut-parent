package com.sxk.actors

import akka.actor.{Actor, ActorLogging, ActorRef}

class StudentActor(teacherActorRef: ActorRef) extends Actor with ActorLogging {

  override def receive: Receive = {
    case "init" => {
      log.info("send teacher ask")
      teacherActorRef ! "ask"
    }
    case _ @other => {
      log.info(s"answer: $other")
    }
  }
}
