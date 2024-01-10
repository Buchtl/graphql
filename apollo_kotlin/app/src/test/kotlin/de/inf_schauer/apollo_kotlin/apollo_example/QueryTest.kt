package de.inf_schauer.apollo_kotlin.apollo_example

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.testing.QueueTestNetworkTransport
import com.apollographql.apollo3.testing.enqueueTestResponse
import de.inf_schauer.apollo_kotlin.apollo_example.gql.DetailsQuery
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class QueryTest {

    @Test
    fun testDetailsQuery() {
        val apolloClient = ApolloClient.Builder().networkTransport(QueueTestNetworkTransport()).build()
        val testQuery = DetailsQuery("4cfe25a2-529e-432e-be08-87ee9094774a")
        val testData = DetailsQuery.Data(DetailsQuery.ProductById("1", "Test", listOf(1), null))

        apolloClient.enqueueTestResponse(testQuery, testData)

        runBlocking {
            val actual = apolloClient.query(testQuery).execute().data!!
            assertEquals(testData, actual)
        }
    }
}