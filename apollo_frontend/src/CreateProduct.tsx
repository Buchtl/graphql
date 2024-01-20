import './App.css';
import { useState, useEffect } from "react";
import { ApolloClient, InMemoryCache, gql, useMutation } from '@apollo/client';
import { Button, TextField, Grid, Box, Input } from '@mui/material';

const ADD_PRODUCT = gql`
  mutation addProduct($name: String) {
    createProduct(name: $name) {
      id
      name
    }
  }
`;

function CreateProduct() {

  const [addProduct, { data, loading, error }] = useMutation(ADD_PRODUCT, {
    variables: {
      name: "placeholder",
    },
  });


  //if (loading) return 'Submitting...';
  //if (error) return `Submission error! ${error.message}`;

  //useEffect(() => {
  //}, [])


  return (
    <div>
      <form
        onSubmit={e => {
          e.preventDefault();
          addProduct({ variables: { name: "abc" } });
        }}
      >
        <button type="submit">Add Todo</button>
      </form>
    </div>
  );
}

export default CreateProduct;