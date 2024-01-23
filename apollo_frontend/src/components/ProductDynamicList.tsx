import './App/App.css';
import { useState, useEffect } from "react";
import { ApolloClient, InMemoryCache, gql, useQuery, useSubscription } from '@apollo/client';
import { List, ListItem, ListItemText, Paper, Grid } from '@mui/material';


interface Product {
  id: string;
  name: string;
}

interface ProductQuery {
  loading?: any;
  data: any;
  error?: any;
}

interface ProductSubscription {
  data?: any;
  loading?: any;
}

const PRODUCT_SUBSCRIPTION = gql`
  subscription  OnProductAdded {
    productAdded {
      id
      name
    }
  }
`

const PRODUCT_QUERY = gql`
  query allProducts {
    allProducts {
      id
      name
    }
  }
`

function ProductDynamicList() {
  const [products, setProducts] = useState<Product[]>([{ id: "", name: "" }])

  const prodSubscr: ProductSubscription = useSubscription(PRODUCT_SUBSCRIPTION);

  const prodQuery: ProductQuery = useQuery(PRODUCT_QUERY)

  const client = new ApolloClient({
    uri: 'http://localhost:8080/graphql',
    cache: new InMemoryCache()
  })

  useEffect(() => {
    if (prodQuery.data) {
      setProducts(prodQuery.data.allProducts)
    }
  }, [prodQuery.data])

  useEffect(() => {
    if (prodSubscr.data) {
      setProducts([...products, { id: prodSubscr.data.productAdded.id, name: prodSubscr.data.productAdded.name }])
    }
  }, [prodSubscr.data])

  return (
    <div className="App-products">
      <h3>Products</h3>
      <div>Recently added: {!prodSubscr.loading && prodSubscr.data.productAdded.name}</div>
      <table>
        <tbody>
          <tr><th>ID</th><th>Name</th></tr>
          {products.map(product => <tr><td>{product.id}</td><td>{product.name}</td></tr>).reverse()}
        </tbody>
      </table>

      <Paper style={{ maxHeight: 200, overflow: 'auto' }}>
        <List>
          {products.map(product => <ListItem>{product.id}{product.name}</ListItem>).reverse()}
        </List>
      </Paper>
    </div>
  );
}

export default ProductDynamicList;