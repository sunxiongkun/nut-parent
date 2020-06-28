package com.sxk

import akka.actor.ActorSystem
import akka.actor.Props
import com.sxk.actors.{StudentActor, TeacherActor}

object AkkaDemo {

  def main(args: Array[String]): Unit = {
    //Initialize the ActorSystem
    val system = ActorSystem.create("AkkaDemoActorSystem")

    //construct the teacher actor
    val teacherRef = system.actorOf(Props[TeacherActor], "teacherActor")
    //construct the Student Actor - pass the teacher actorref as a constructor parameter to StudentActor
    val studentRef =
      system.actorOf(Props(new StudentActor(teacherRef)), "studentActor")
    //send a message to the Student Actor
    studentRef ! "init"
    //Let's wait for a couple of seconds before we shut down the system
    Thread.sleep(2000)

    //Shut down the ActorSystem.
    system.terminate()

  }

}
