import './CreateProduct.css';
import { useState, ChangeEvent } from "react";
import { gql, useMutation } from '@apollo/client';
import { FormEvent } from 'react';
import { Button, TextField, Input, Box, Grid } from '@mui/material';

const ADD_PRODUCT = gql`
  mutation addProduct($name: String) {
    createProduct(name: $name) {
      id
      name
    }
  }
`;

interface FormData {
  name: string;
}

function CreateProduct() {

  const [addProduct, { data, loading, error }] = useMutation(ADD_PRODUCT, {
    variables: {
      name: "placeholder",
    },
  });

  const [formData, setFormData] = useState<FormData>({ name: '' })

  const handleInputChange = (e: ChangeEvent<HTMLInputElement>): void => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    })
  }

  const handleSubmit = (e: FormEvent<HTMLFormElement>): void => {
    e.preventDefault();
    console.log("Handled: ", formData.name);
    addProduct({ variables: { name: formData.name } })
  }

  return (
    <div>
      <Box className='MyBox'>
        <form onSubmit={handleSubmit}>
          <Grid container spacing={1}>
            <Grid item xs={6}>
              <TextField variant="standard" type="text" id="name" name="name" value={formData.name} onChange={handleInputChange}></TextField>
            </Grid>
            <Grid item xs={4}>
              <Button type="submit" variant="contained" color="success" >Add Product</Button>
            </Grid>
          </Grid>
        </form>
      </Box>
    </div >
  );
}

export default CreateProduct;