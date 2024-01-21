import './App.css';
import { useState, useEffect } from "react";
import { ApolloClient, InMemoryCache, gql, useSubscription } from '@apollo/client';
import { List, ListItem, ListItemText, Paper } from '@mui/material';


interface Product {
  id: string;
  name: string;
}

const PRODUCT_SUBSCRIPTION = gql`
  subscription  OnProductAdded {
    productAdded {
      id
      name
    }
  }
`

function ProductDynamicList() {
  const [products, setProducts] = useState<Product[]>([{ id: "", name: "" }])

  const { data, loading } = useSubscription(PRODUCT_SUBSCRIPTION);

  const client = new ApolloClient({
    uri: 'http://localhost:8080/graphql',
    cache: new InMemoryCache()
  })

  useEffect(() => {
    var response = client.query({
      query: gql`
                query allProducts {
                    allProducts {
                      id
                      name
                    }
                }
                `,
    })
    response.then(r => r.data).then(data => { setProducts(data.allProducts) })
  }, [])

  useEffect(() => {
    if (data) {
      setProducts([...products, { id: data.productAdded.id, name: data.productAdded.name }])
    }
  }, [data])

  return (
    <div className="App-products">
      <h3>Products</h3>
      <div>Recently added: {!loading && data.productAdded.name}</div>
      <table>
        <tbody>
          <tr><th>ID</th><th>Name</th></tr>
          {products.map(product => <tr><td>{product.id}</td><td>{product.name}</td></tr>).reverse()}
        </tbody>
      </table>
      <Paper style={{ maxHeight: 200, overflow: 'auto' }}>
        <List>
          {products.map(product => <ListItem>{product.id}{product.name}</ListItem>)}
        </List>
      </Paper>
    </div>
  );
}

export default ProductDynamicList;