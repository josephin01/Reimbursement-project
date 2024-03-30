import React from "react";
import AppRoutes from "./router/appRouters";
import { PersistGate } from "redux-persist/integration/react";
import { persistor } from "./redux/store";
import "./App.scss";


function App() {
  // const [side, setSide] = useState('Home');
  return (
    <div className="App">
      <PersistGate loading={null} persistor={persistor}>
        <AppRoutes />
      </PersistGate>
     
    </div>
  );
}

export default App;
