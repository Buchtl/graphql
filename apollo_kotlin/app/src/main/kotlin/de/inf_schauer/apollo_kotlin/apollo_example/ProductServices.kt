package de.inf_schauer.apollo_kotlin.apollo_example

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import de.inf_schauer.apollo_kotlin.apollo_example.gql.CreateMutation
import de.inf_schauer.apollo_kotlin.apollo_example.gql.DetailsQuery
import kotlinx.coroutines.async
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
    }
}
