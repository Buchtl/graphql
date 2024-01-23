import React from "react";
import "./Layout.css";
import Header from "../Header/Header";

export default function Layout( {children} : any ) {
  return (
    <div>
      <Header/>
      {children}
    </div>
  );
}