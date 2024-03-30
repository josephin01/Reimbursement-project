import React, { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { constant } from "../../constants/constant";
import Empdetails from "../../components/empdetails/empDetails";
import { Greeting } from "../../components/greeting/greeting";
import ImageSlider from "../../components/imageSlider/imageSlider";
import { LoginComponent } from "../../components/loginComponent/loginComponent";
import "./login.scss";

function Login() {
  const component = useSelector((state) => state.auth.component);

  return (  
    <div className="index">
      <div className="index-wrapper">
        <div className="index-images">
          <div className="circle-1"></div>
          <div className="typewriter">
            <h1>
              <Greeting />
            </h1>
            <p className="index-quotes">{constant.login_quotes}</p>
            <p className="index-quotes">{constant.login_quotes_author}</p>
          </div>
          <div className="index-image">
            <ImageSlider />
          </div>
          <div className="circle-2"></div>
        </div>
        <div className="index-component">
          {component ? <Empdetails /> : <LoginComponent />}
        </div>
      </div>
    </div>
  );
}

export default Login;
