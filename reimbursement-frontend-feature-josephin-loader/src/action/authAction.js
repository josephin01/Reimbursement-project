import axios from "axios";
import {
  setAccessToken,
  setComponent,
  setRefreshToken,
} from "../redux/slice/authSlice";

import { Store } from '../redux/store';
import { setUser } from "../redux/slice/userSlice";
import { Navigate, Outlet } from "react-router-dom";

export const userLogin = async (Token, navigate, dispatch) => {
  try {
    const response = await axios.post(
      "http://localhost:8000/api/v1/auth",
      Token,
      {
        headers: {
          "Content-Type": "application/json",
        },
      }
    );
    const data = await response.data;
    if (data.message === "User Not Found") {
      dispatch(setComponent(true));
    } else {
      dispatch(setAccessToken(data.data.accessToken));
      dispatch(setRefreshToken(data.data.refreshToken));
      dispatch(setUser(response.data.data.employeeDetails));
      navigate("/Dashboard");
      dispatch(setComponent(false));
    }
  } catch (err) {
    console.log(err);
  }
};

export const handleSubmit = async (formData, setFormData, dispatch) => {
  try {
    const response = await axios.post(
      "http://localhost:8000/api/v1/auth/addUserDetails",
      formData
    );
    console.log(response);
    dispatch(setAccessToken(response.data.data.accessToken));
    dispatch(setRefreshToken(response.data.data.refreshToken));
    dispatch(setUser(response.data.data.employeeDetails));
    console.log(response.data.data.employeeDetails,"[][]")

  } catch (error) {
    console.log(error);
  }
  setFormData({
    firstName: "",
    lastName: "",
    contact: "",
    email: "",
    dob: "",
    empId: "",
  });
};

export const oAUthlogout = async () => {
  const { accessToken } = Store.getState().auth;
  try {
    const response = await axios.post(
      "http://localhost:8000/api/v1/auth/logout",
      accessToken,
      {
        headers: {
          "Content-Type": "application/json",
        },
      }
    );
    console.log(response);

    if (response.data.message === "Logged Out Successfully") {
      // dispatch(setComponent(false));
      window.location.href = "/";
    }
  } catch (error) {
    console.log(error);
  }
};

export const ProtectRouter = () => {
  const user = true;

  return user ? <Outlet/> : <Navigate to = "/"/>
}