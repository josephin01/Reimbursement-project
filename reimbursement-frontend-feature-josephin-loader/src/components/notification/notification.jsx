


import React from "react";
import './notification.scss'
import { IoMdCloseCircleOutline } from "react-icons/io";
import { employeeNotificationAPI } from "../../action/api/Api_config";
import user from '../../assets/mockData/mockData';

function Notification({ data }) {

  const handleClose=()=>{

  }
 
  return (
    <div className="notificationContent">
      <div className="notificationTitle">Notifications</div>
      {data.map((value,key)=>(
        <div className="notificationMessage">
            {value.empId?
            <div>
            {value.empId} has Submitted a new {value.formType=="Expense_Form"?"Expense Form":value.formType=="Travel_Form"?"Travel From":"Expense Claim"}
            </div>:
            <div>
            Your {value.formType=="Expense_Form"?"Expense Form":value.formType=="Travel_Form"?"Travel From":"Expense Claim"} Submitted on {value.date} has been {value.status=="Approved"?"Approved":"Rejected"}
            </div>}
            <div onClick={handleClose}><IoMdCloseCircleOutline /></div>
        </div>
      ))}

    </div>
  );
}

export default Notification;