import React, { useState, useContext, useEffect } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import AddTravelForm from "../../../components/forms/travelForm/travelForm";
import Button from "../../../components/buttons/buttons";
import {
  purposeData,
  collegueData,
  projectNameData,
  travelFormTable,
} from "../../../action/api/Api_config";
import { AppContext } from "../../../context/context";
import './travelFormAdd.scss'
import 'react-toastify/dist/ReactToastify.css'

import { ToastContainer, toast } from 'react-toastify';

const  TravelFormAdd=()=> {
  const [projectName, setProjectName] = useState([]);
  const [purpose, setPurpose] = useState([{ id: "", purpose: "" }]);
  const [Colleague, setCollegue] = useState([]);
  const { addData } = useContext(AppContext);
  const [addDataValue, setAddDataValue] = addData; 
  const [success,setSuccess]=useState(false)
  const [failure,setFailure]=useState(false)
  const [loading, setLoading] = useState(true);
  const moment = require("moment");

  const handleSubmit =(addDataValue) => {
    travelFormTable(addDataValue)
    .then((response)=>{
      if(response.status==200){
        setSuccess(true)
        setTimeout(()=>{
          setSuccess(false)
          window.history.back()
        },3000)
      }
      else{
        setFailure(true)
        setTimeout(()=>{
          setFailure(false)
          window.history.back()
        },3000)
      }
    })
  }
  useEffect(() => {
    purposeData(setPurpose, setLoading);
  }, []);



  useEffect(() => {
    projectNameData(setProjectName);
  }, []);
  return (
    <div>
      {success?<div className="successMessage1">
        Travel Form Added Successfully
      </div>:failure?<div className="errorMessage">
        Some error occured. Try again
      </div>:null}
      <AddTravelForm
        className="addTravelForm"
        setAddDataValue={setAddDataValue}
        Purpose={purpose}
        ColleagueName={Colleague}
        projectName={projectName}
      />
      <div className="buttonClass">
        <div onClick={() => handleSubmit(addDataValue)}>
          <Button Data="Apply" />
        </div>
      </div>
    </div>
  );
}

export default TravelFormAdd;
