package de.inf_schauer.apollo_kotlin.apollo_example

import org.springframework.messaging.converter.MappingJackson2MessageConverter
import org.springframework.messaging.simp.stomp.StompHeaders
import org.springframework.messaging.simp.stomp.StompSession
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
import org.springframework.web.socket.client.standard.StandardWebSocketClient

import org.springframework.web.socket.messaging.WebSocketStompClient

class WebsocketServices {

    companion object {
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
        fun playground() {
            val urlWebsocket = "ws://localhost:8080/my-websocket"
            val stompClient = WebSocketStompClient(StandardWebSocketClient())
            stompClient.messageConverter = MappingJackson2MessageConverter()

            val taskScheduler = ThreadPoolTaskScheduler()
            taskScheduler.afterPropertiesSet()
            stompClient.taskScheduler = taskScheduler

            val sessionHandler = MyStompSessionHandler()

            val stompSession = stompClient.connectAsync(urlWebsocket, sessionHandler).get()
            while (true) {
                Thread.sleep(1000)
                stompSession.send("/app/hello", "Hello, Spring! ${System.currentTimeMillis()}")
            }
        }
    }
}