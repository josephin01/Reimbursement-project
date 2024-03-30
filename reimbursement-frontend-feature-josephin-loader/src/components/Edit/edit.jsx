import React from "react";
import "../Edit/edit.scss";
import { GiCancel } from "react-icons/gi";
import { useState } from "react";
import { roleChanging } from "../../action/api/Api_config";
import Button from "../buttons/buttons";
import { employeeEditFormData } from "../../constants/constant";

const Edit = ({ employeeData, setEditState }) => {

  const [failure,setFailure]=useState("");
  const [success,setSuccess] = useState("");

  const roles = [
    {
      value: "EMPLOYEE",
      display_name: "Employee",
    },
    {
      value: "MANAGER",
      display_name: "Manager",
    },
    {
      value: "ADMIN",
      display_name: "Admin",
    },
  ];

  const [role, SetRole] = useState();
  const [data, setData] = useState({
    // name: employeeData.employeeName,
    empId: employeeData.empId,
    roleName: "",
  });


  const handleChange = (e) => {
    const { name, value } = e.target;
    setData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleEdit = () => {
    setEditState(false);
  };

  const rolechanged = (e) => {
    e.preventDefault();
    roleChanging(data).then((response)=>{
      if(response.status==200){
        setSuccess(true)
        setTimeout(()=>{
          setSuccess(false)
        },3000)
      }
      else{
        setFailure(true)
        setTimeout(()=>{
          setFailure(true)
        },3000)
      }
    })
    setEditState(false);
  };

  return (
    <div className="admin-edit">
     {success?<div className="successMessage1">
        People Updated Successfully
      </div>:failure?<div className="errorMessage">
        Some error occured. Try again
      </div>:null}
      <form action="" className="employeeEditForm">
        <GiCancel className="editFormCancelIcon" onClick={handleEdit} />
        <div className="employeeEditFormInputs">
          <label>{employeeEditFormData.EmployeeName} </label>{" "}
          <input
            type="text"
            className="employeeEditFormInput"
            value={employeeData.employeeName}
          />
        </div>
        <div className="employeeEditFormInputs">
          <label>{employeeEditFormData.Employee_ID}</label>{" "}
          <input
            type="text"
            className="employeeEditFormInput"
            value={employeeData.empId}
            onChange={handleChange}
            name="empId"
          />
        </div>
        <div className="employeeEditFormInputs">
          <label>{employeeEditFormData.Employee_Dob}</label>{" "}
          <input
            type="date"
            className="employeeEditFormInput employeeEditFormDateInput"
            value={
              employeeData.dob
                ? new Date(employeeData.dob).toISOString().substring(0, 10)
                : ""
            }
          />
        </div>
        <div className="employeeEditFormInputs">
          <label>{employeeEditFormData.Employee_Role}</label>
          <select
            className="employeeEditFormSelect"
            defaultValue={employeeData.role}
            name="roleName"
            onChange={handleChange}
          >
            {roles.map((role) => (
              <option key={role.value} value={role.value}>
                {role.display_name}
              </option>
            ))}
          </select>
        </div>
        <div className="employeeEditFormInputs">
          <label>{employeeEditFormData.Employee_Contact}</label>{" "}
          <input
            type="text"
            className="employeeEditFormInput"
            value={employeeData.phone}
          />
        </div>
        <div className="editSubmitButton" onClick={rolechanged}>
          <Button Data="Submit" />
        </div>
      </form>
    </div>
  );
};

export default Edit;
