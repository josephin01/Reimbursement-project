import axios from "axios";
import { setAccessToken, setRefreshToken } from "../../redux/slice/authSlice";
import { Store } from "../../redux/store";

axios.defaults.baseURL = "http://localhost:8000/api/v1";

axios.interceptors.request.use(
  async (config) => {
    const { accessToken, refreshToken } = Store.getState().auth;
    if (refreshToken && config.url?.includes("/auth/regenerateToken")) {
        config.data = { refreshToken };
      delete config.headers.Authorization;
    } else if (accessToken) {
      config.headers.Authorization = `Bearer ${accessToken}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

axios.interceptors.response.use(
  (response) => response,
  async (error) => {
    const errorMessage = error.response ? error.response.data.message : null;
    const originalRequest = error.config;
    if (errorMessage === "JWT Token Expired") {
      const { refreshToken } = Store.getState().auth;      
      try {
        const response = await axios.post('/auth/regenerateToken',{refreshToken});
        const data = response.data;
        const newAccessToken = data.data.accessToken;
        const newRefreshToken = data.data.refreshToken;

        Store.dispatch(setAccessToken(newAccessToken));
        Store.dispatch(setRefreshToken(newRefreshToken));


        originalRequest.headers.Authorization = `Bearer ${newAccessToken}`;
        
      } catch (error) {
        console.log(error);
      }
      return axios(originalRequest);
    } else if (errorMessage === "Token Expired") {
      window.location.href = "/";
    } else {
      return Promise.reject(error);
    }
  }
);

export default axios;