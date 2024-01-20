import './App.css';
import { useState, useEffect } from "react";
import { ApolloClient, InMemoryCache, gql } from '@apollo/client';
import { Button, TextField, Grid, Box } from '@mui/material';

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
    <div className="App-body">
      hello apollo and {value}
      <Box component="form">
        <div>
          <TextField id="product_name" label='Product Name' variant='outlined'></TextField>
          <Button variant="contained">Create Product</Button>
        </div>
      </Box>
    </div>
  );
}

export default HelloApollo;