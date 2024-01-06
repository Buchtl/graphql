package de.inf_schauer.apollo_kotlin.apollo_example

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import de.inf_schauer.apollo_kotlin.apollo_example.gql.CreateMutation
import de.inf_schauer.apollo_kotlin.apollo_example.gql.DetailsQuery
import kotlinx.coroutines.*
import java.util.*


class Product {
    companion object {

        private fun executeInBlock(runBlockingCode: (apolloClient: ApolloClient, CoroutineScope) -> Unit) {
            val apolloClient = ApolloClient.Builder().serverUrl("http://localhost:8080/graphql").build()
            val job = Job()
            val coroutineScope = CoroutineScope(Dispatchers.Default + job)
            runBlockingCode(apolloClient, coroutineScope)
        }

        fun details(id: UUID) {
            val toRun: (ApolloClient, CoroutineScope) -> Unit = { apolloClient, coroutineScope ->


                val detailsQuery = DetailsQuery(id.toString())
                val deferredResponse = coroutineScope.async {
                    apolloClient.query(detailsQuery).execute()
                }
                runBlocking {
                    try {
                        val response = deferredResponse.await()
                        if (response.hasErrors()) {
                            // Handle errors
                            response.errors?.forEach { error ->
                                println("GraphQL Error: ${error.message}")
                            }
                        } else {
                            // Handle successful response
                            val product = response.data?.productById
                            println("Product ID: ${product?.id}")
                            println("Product Name: ${product?.name}")
                            println("Product Number: ${product?.product_no}")
                            val manufacturer = product?.manufacturer
                            println("Manufacturer ID: ${manufacturer?.id}")
                            println("Manufacturer Name: ${manufacturer?.name}")
                        }
                    } finally {
                        apolloClient.close()
                    }
                }
            }

            executeInBlock(toRun)
        }


        fun create(name: String, productNo: String) {
            val toRun: (ApolloClient, CoroutineScope) -> Unit = { apolloClient, coroutineScope ->

                val createMutation = CreateMutation(Optional.present(name), Optional.present(productNo))

                val deferredResponse = coroutineScope.async {
                    apolloClient.mutation(createMutation).execute()
                }
                runBlocking {
                    try {
                        val response = deferredResponse.await()
                        if (response.hasErrors()) {
                            // Handle errors
                            response.errors?.forEach { error ->
                                println("GraphQL Error: ${error.message}")
                            }
                        } else {
                            // Handle successful response
                            val product = response.data?.createProduct
                            println("Product ID: ${product?.id}")
                            println("Product Name: ${product?.name}")
                            println("Product Number: ${product?.product_no}")
                            val manufacturer = product?.manufacturer
                            println("Manufacturer ID: ${manufacturer?.id}")
                            println("Manufacturer Name: ${manufacturer?.name}")
                        }
                    } finally {
                        apolloClient.close()
                    }
                }
            }
            executeInBlock(toRun)
        }
    }
}
