import './App.css';
import React, { useState, useEffect } from "react";
import { ApolloClient, InMemoryCache, ApolloProvider, gql } from '@apollo/client';

function HelloApollo() {

    const [value, setValue] = useState("")

    const client = new ApolloClient({
        uri: 'http://localhost:8080/graphql',
        cache: new InMemoryCache()
    })

    useEffect(() => {
        client.query({
            query: gql`
                query helloApollo {
                    helloApollo {
                      id
                      name
                    }
                }
                `,
        }).then((result) => setValue(result.data.helloApollo.name));
    }, [])


    return (
        <div className="App-header">
            hello apollo and {value}
        </div>
    );
}

export default HelloApollo;