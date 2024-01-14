package de.inf_schauer.grapqhl.subscriber_server.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.converter.MappingJackson2MessageConverter
import org.springframework.messaging.simp.stomp.StompHeaders
import org.springframework.messaging.simp.stomp.StompSession
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.socket.client.standard.StandardWebSocketClient
import org.springframework.web.socket.messaging.WebSocketStompClient


@RestController
class HelloController {

  @Autowired
  lateinit var webClientBuilder: WebClient.Builder

  val URL_WEBSOCKET = "ws://localhost:8080/my-websocket"


  @GetMapping("/hello")
  fun hello(): String {
    return "Hello World!"
  }

  class MyStompSessionHandler : StompSessionHandlerAdapter() {
    override fun afterConnected(session: StompSession, connectedHeaders: StompHeaders) {
      println("Connected to WebSocket")
    }

    override fun handleFrame(headers: StompHeaders, payload: Any?) {
      println("Received message: $payload")
    }

    override fun handleTransportError(session: StompSession, exception: Throwable) {
      println("Got an error ##################### ${exception.message}")
    }
  }

  @GetMapping("/subscribe")
  fun subscribe(): String {

    val stompClient = WebSocketStompClient(StandardWebSocketClient())
    val sessionHandler = MyStompSessionHandler()
    val stompSession = stompClient.connect(URL_WEBSOCKET, sessionHandler)
      .get()

    // Subscribe to the "/topic/messages" destination
    val sub = stompSession.subscribe("/topic/greetings", sessionHandler)
    println("###################### ${sub.subscriptionId}")
    stompSession.setAutoReceipt(true)

    while (true) {
      Thread.sleep(1000)
      sub.subscriptionHeaders.let {
        println("###################### ${it}")
      }
    }

    return "............"
  }

  @GetMapping("/push")
  fun push() {

    val stompClient = WebSocketStompClient(StandardWebSocketClient())
    stompClient.messageConverter = MappingJackson2MessageConverter()

    val taskScheduler = ThreadPoolTaskScheduler()
    taskScheduler.afterPropertiesSet()
    stompClient.taskScheduler = taskScheduler

    val sessionHandler = MyStompSessionHandler()

    val stompSession = stompClient.connect(URL_WEBSOCKET, sessionHandler)
      .get()
    while (true) {
      Thread.sleep(1000)
      stompSession.send("/app/hello", "Hello, Spring! ${System.currentTimeMillis()}")
    }
  }
}