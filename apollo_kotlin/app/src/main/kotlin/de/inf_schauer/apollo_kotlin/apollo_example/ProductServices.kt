package de.inf_schauer.apollo_kotlin.apollo_example

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.network.ws.WebSocketNetworkTransport
import de.inf_schauer.apollo_kotlin.apollo_example.gql.CreateMutation
import de.inf_schauer.apollo_kotlin.apollo_example.gql.DetailsQuery
import de.inf_schauer.apollo_kotlin.apollo_example.gql.ProductSubscription
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import java.util.*


class ProductServices {
    companion object {

        private fun createApolloClient(): ApolloClient =
            ApolloClient.Builder().serverUrl("http://localhost:8080/graphql").build()

        fun details(id: UUID): UUID {
            val apolloClient = createApolloClient()
            val detailsQuery = DetailsQuery(id.toString())

            return runBlocking {
                apolloClient.use { apolloClient ->
                    val response = async { apolloClient.query(detailsQuery).execute() }.await()
                    return@runBlocking UUID.fromString(response.data?.productById?.id)
                }
            }
        }


        fun create(name: String, productNo: List<Int?>): UUID {
            val apolloClient = createApolloClient()
            val createMutation = CreateMutation(Optional.present(name), Optional.present(productNo))

            return runBlocking {
                apolloClient.use { apolloClient ->
                    val response = async { apolloClient.mutation(createMutation).execute() }.await()
                    return@runBlocking UUID.fromString(response.data?.createProduct?.id)
                }
            }
        }

        fun subscribe() {
            val apolloClient = ApolloClient.Builder().subscriptionNetworkTransport(
                WebSocketNetworkTransport.Builder().serverUrl("ws://localhost:8080/graphql").build()
            ).serverUrl("ws://localhost:8080/graphql").build()
            val subscriptionQuery = ProductSubscription()

            runBlocking {
                apolloClient.use { apolloClient ->
                    apolloClient.subscription(subscriptionQuery).toFlow().onEach { println("Eacht ${it.data?.productAdded?.id}") }
                }
            }
        }
    }
}
