package de.inf_schauer.apollo_kotlin.apollo_example

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import de.inf_schauer.apollo_kotlin.apollo_example.gql.CreateMutation
import de.inf_schauer.apollo_kotlin.apollo_example.gql.DetailsQuery
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import java.util.*


class Product {
    companion object {

        private fun createApolloClient(): ApolloClient =
            ApolloClient.Builder().serverUrl("http://localhost:8080/graphql").build()

        fun details(id: UUID) {
            val apolloClient = createApolloClient()
            val detailsQuery = DetailsQuery(id.toString())

            runBlocking {
                apolloClient.use { apolloClient ->
                    val response = async { apolloClient.query(detailsQuery).execute() }.await()

                    if (response.hasErrors()) {
                        // Handle errors
                        response.errors?.forEach { error ->
                            println("GraphQL Error: ${error.message}")
                        }
                    } else {
                        // Handle successful response
                        println(response.data?.productById)
                    }
                }
            }
        }

        fun create(name: String, productNo: String) {
            val apolloClient = createApolloClient()
            val createMutation = CreateMutation(Optional.present(name), Optional.present(productNo))

            runBlocking {
                apolloClient.use { apolloClient ->
                    val response = async { apolloClient.mutation(createMutation).execute() }.await()
                    if (response.hasErrors()) {
                        // Handle errors
                        response.errors?.forEach { error ->
                            println("GraphQL Error: ${error.message}")
                        }
                    } else {
                        // Handle successful response
                        println(response.data?.createProduct)
                    }
                }
            }
        }
    }
}
