package de.inf_schauer.grapqhl.subscriber_server.controller

import org.springframework.messaging.converter.MappingJackson2MessageConverter
import org.springframework.messaging.converter.StringMessageConverter
import org.springframework.messaging.simp.stomp.StompHeaders
import org.springframework.messaging.simp.stomp.StompSession
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.socket.client.standard.StandardWebSocketClient
import org.springframework.web.socket.messaging.WebSocketStompClient


@RestController
class HelloController {

  val urlWebsocket = "ws://localhost:8080/my-websocket"

  @GetMapping("/hello")
  fun hello(): String {
    return "Hello World!"
  }

  class MyStompSessionHandler : StompSessionHandlerAdapter() {
    override fun afterConnected(session: StompSession, connectedHeaders: StompHeaders) {
      println("Connected to WebSocket")
    }

    override fun handleFrame(headers: StompHeaders, payload: Any?) {
      // Handle incoming messages here
      println("Received payload: $payload")
    }

    override fun handleTransportError(session: StompSession, exception: Throwable) {
      println("Got an error ##################### ${exception.message}")
    }
  }

  @GetMapping("/subscribe")
  fun subscribe(): String {
    val stompClient = WebSocketStompClient(StandardWebSocketClient())
    stompClient.messageConverter = StringMessageConverter()

    val sessionHandler = MyStompSessionHandler()
    val stompSession = stompClient.connectAsync(urlWebsocket, sessionHandler)
      .get()

    val subscription = stompSession.subscribe("/topic/greetings", sessionHandler)
    stompSession.send("/app/hello", "World")

    println("waiting... ${subscription.subscriptionId}")

    Thread.sleep(10000)
    subscription.unsubscribe()
    stompSession.disconnect()
    return "done"
  }

  @GetMapping("/push")
  fun push(): String {

    val stompClient = WebSocketStompClient(StandardWebSocketClient())
    stompClient.messageConverter = MappingJackson2MessageConverter()

    val taskScheduler = ThreadPoolTaskScheduler()
    taskScheduler.afterPropertiesSet()
    stompClient.taskScheduler = taskScheduler

    val sessionHandler = MyStompSessionHandler()

    val stompSession = stompClient.connectAsync(urlWebsocket, sessionHandler)
      .get()

    for (i in 1..10) {
      Thread.sleep(1000)
      val payload = "Hello, Spring! ${System.currentTimeMillis()}"
      println("Send $payload")
      stompSession.send("/app/hello", payload)
    }
    stompSession.disconnect()
    return "done"
  }
}