import { useGoogleLogin } from "@react-oauth/google";
import React from "react";
import { GoogleButton } from "react-google-button";
import { userLogin } from "../../action/authAction";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { useDispatch } from "react-redux";
export const Oauth = () => {
  const navigate = useNavigate()
  const dispatch = useDispatch()
  const login = useGoogleLogin({
    onSuccess: async (tokenResponse) => {
      await userLogin(tokenResponse.access_token,navigate,dispatch);
    },
  });
  return <GoogleButton onClick={login} />;
};
