import './App/App.css';
import { useState, useEffect } from "react";
import { ApolloClient, InMemoryCache, gql } from '@apollo/client';

interface Product {
  id: string;
  name: string;
}

function ProductList() {

  const [products, setProducts] = useState<Product[]>([{ id: "", name: "" }])


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


  return (
    <div className="App-body">
      <h2>Products</h2>
      <table>
        <tbody>
          <tr><th>ID</th><th>Name</th></tr>
          {products.map(product => <tr><td>{product.id}</td><td>{product.name}</td></tr>)}
        </tbody>
      </table>
    </div>
  );
}

export default ProductList;