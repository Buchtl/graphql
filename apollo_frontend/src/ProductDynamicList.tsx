import './App.css';
import { useState, useEffect } from "react";
import { ApolloClient, InMemoryCache, gql,useSubscription } from '@apollo/client';


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
  
  const { data, loading} = useSubscription(PRODUCT_SUBSCRIPTION);
  
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

  /*useEffect(() => {
    let tmp: Product = {id: data.productAdded.name, name: data.productAdded.name}
    console.log("++++++++++++++++ " + tmp.name)
  }, [data])*/


  //<h2>{!loading && data.productAdded.name}</h2>
  return (
    <div className="App-body">
      <h2>Latest</h2>
      <div>{!loading && data.productAdded.name}</div>
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

export default ProductDynamicList;