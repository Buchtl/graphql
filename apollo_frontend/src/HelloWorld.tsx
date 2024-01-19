import './App.css';
import React, { useState, useEffect } from "react";

function HelloWorld() {

  const [value, setValue] = useState("")

  useEffect(() => {
    fetch('http://localhost:8080/hello',
      {
        method: "GET"
      }
    )
      .then((response) => response.text())
      .then((data) => {
        console.log(data);
        setValue(data)
      })
  }, [])


  return (
    <div className="App-header">
        hello react and {value}
    </div>
  );
}

export default HelloWorld;
