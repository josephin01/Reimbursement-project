import React from "react";
import "./loginComponent.scss";
import { GoogleOAuthProvider } from "@react-oauth/google";
import { Oauth } from "../oAuth/oAuth";
import { constant } from "../../constants/constant";
import divumLogo from "../../assets/images/DivumLogo.jpeg";

export const LoginComponent = () => {
  return (
    <div className="login-component">
      <div className="login-wrapper">
        <div className="login-logo">
          <img src={divumLogo} alt="" className="logo-image" />
          <span className="text">{constant.login_gretting} &#128075; </span>
        </div>
        <div className="login-google">
          <GoogleOAuthProvider clientId="103138130018-v3tpihc0ntt1vj1i3ntqkmohi0vu6bgj.apps.googleusercontent.com">
            <Oauth />
          </GoogleOAuthProvider>
        </div>
      </div>
    </div>
  );
};
