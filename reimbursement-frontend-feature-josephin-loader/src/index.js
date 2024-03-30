import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App';
import { BrowserRouter } from 'react-router-dom';
import { Provider } from 'react-redux';
import {Store} from './redux/store'
import { ContextProvider } from './context/context';
// import 'react-toastify/dist/ReactToastify.css';


const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <ContextProvider>
    <Provider store={Store}>

      <BrowserRouter>
        <App />
      </BrowserRouter>
    </Provider>
  </ContextProvider>
);

