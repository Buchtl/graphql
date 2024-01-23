import React from "react";
import "./Layout.css";
import Header from "../Header/Header";
import Footer from "../Footer/Footer";

export default function Layout( {children} : any ) {
  return (
    <div>
      <Header/>
      {children}
      <Footer/>
    </div>
  );
}