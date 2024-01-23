import React, { useState, useEffect } from "react";

function Header() {

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
    <header>
      hello react and {value}
    </header>
  );
}


export default Header;
