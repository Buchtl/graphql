import './App.css';
import { useState, ChangeEvent } from "react";
import { gql, useMutation } from '@apollo/client';
import { FormEvent } from 'react';

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

  const [formData, setFormData] = useState<FormData>({name: ''})

  const handleInputChange = (e: ChangeEvent<HTMLInputElement>): void => {
    const {name, value} = e.target;
    setFormData({
      ...formData,
      [name]: value,
    })
  }

  const handleSubmit = (e: FormEvent<HTMLFormElement>): void => {
    e.preventDefault();
    console.log("Handled: ", formData.name);
    addProduct({variables: { name: formData.name}})
  }

  return (
    <div>
      <form
        onSubmit={handleSubmit}
      >
        <input type="text" id="name" name="name" value={formData.name}  onChange={handleInputChange}></input>
        <button type="submit">Add Todo</button>
      </form>
    </div>
  );
}

export default CreateProduct;