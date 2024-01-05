/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package de.inf_schauer.apollo_kotlin.apollo_example

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import de.inf_schauer.apollo_kotlin.apollo_example.gql.DetailsQuery
import kotlinx.coroutines.*
import okhttp3.OkHttpClient

class ApolloExample {
    fun run() {
        val apolloClient =
            ApolloClient.Builder().serverUrl("http://localhost:8080/graphql").okHttpClient(OkHttpClient()).build()

        val detailsQuery = DetailsQuery("4cfe25a2-529e-432e-be08-87ee9094774a")

        val job = Job()
        val coroutineScope = CoroutineScope(Dispatchers.Default + job)

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
                println("finally")
                apolloClient.close()
            }
        }
    }
}

fun main() {
    ApolloExample().run()
}
