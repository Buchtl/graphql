package de.inf_schauer.apollo_kotlin.apollo_example

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.mockserver.MockResponse
import com.apollographql.apollo3.mockserver.MockServer
import de.inf_schauer.apollo_kotlin.apollo_example.gql.DetailsQuery
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

class ProductServicesTest {

    @Test
    fun testDetailsQuery() {
        val expected = DetailsQuery.Data(DetailsQuery.ProductById("1", "Test", listOf(1), null))
        val mockServer = MockServer()

        MockResponse.Builder()
            .body("""{"data":{"productById":{"id":"1","name":"Test","product_no": [1],"manufacturer":null}}}""")
            .headers(mapOf("X-Test" to "true")).statusCode(200).build().let { mockServer.enqueue(it) }

        runBlocking {
            val apolloClient = ApolloClient.Builder().serverUrl(mockServer.url()).build()
            val actual = apolloClient.query(DetailsQuery(UUID.randomUUID().toString())).execute().data!!
            assertEquals(expected, actual)
            mockServer.stop()
        }
    }
}
