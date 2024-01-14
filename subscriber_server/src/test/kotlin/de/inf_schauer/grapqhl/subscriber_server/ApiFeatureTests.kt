package de.inf_schauer.grapqhl.subscriber_server

import io.netty.handler.codec.http.HttpHeaderValues
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApiFeatureTests {

  @Autowired
  lateinit var webTestClient: WebTestClient

  @Test
  fun `hello endpoint returns Hello World!`() {
    this.webTestClient.get()
      .uri("/hello")
      .header(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.TEXT_PLAIN.toString())
      .exchange()
      .expectStatus()
      .isOk()
      .expectBody(String::class.java)
      .isEqualTo("Hello World!")
  }
}