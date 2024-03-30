import React, { useState } from "react";
import "./empDetails.scss";
import { validation } from "./empValidation";
import { handleSubmit } from "../../action/authAction";
import { employeeForm } from "../../constants/constant";
import { useDispatch } from "react-redux";
import { setUser } from "../../redux/slice/userSlice";
import { useNavigate } from "react-router-dom";

const Empdetails = () => {
  const [isvalid, setIsvalid] = useState(false);
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    firstName: "",
    lastName: "",
    contact: "",
    email: "",
    dob: "",
    empId: "",
  });

  const [errors, setErrors] = useState({
    firstName: "",
    lastName: "",
    contact: "",
    email: "",
    empId: "",
  });

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleBlur = (e) => {
    const isValid =
      validation(e.target.name, e.target.value, errors, setErrors) &&
      (e.target.value === "" ? false : true);
    setIsvalid(isValid);
  };

  const formSubmit = (e) => {
    e.preventDefault();
    navigate("Dashboard");
    const isFormFilled = Object.values(formData).every((value) => value !== "");
    const sss =
      isvalid &&
      isFormFilled &&
      handleSubmit(formData, setFormData, dispatch) &&
      dispatch(setUser(formData));
  };


  return (
    <div className="Login-form">
      <div className="Login-form-wrapper">
        <div className="Login-form-headers">
          <h1>{employeeForm.formHeader}</h1>
          <p>{employeeForm.formDescription}</p>
        </div>
        <form className="Login-form-form" onSubmit={formSubmit}>
          <div className="login-body-container">
            <div className="text-body">
              <div className="text-input">
                <input
                  type="text"
                  id="firstName"
                  name="firstName"
                  value={formData.firstName}
                  onChange={handleChange}
                  onBlur={handleBlur}
                />
                <label htmlFor="firstName">{employeeForm.firstName}</label>
                <div className="error">{errors.firstName}</div>
              </div>
              <div className="text-input">
                <input
                  type="text"
                  id="lastName"
                  name="lastName"
                  value={formData.lastName}
                  onChange={handleChange}
                  onBlur={handleBlur}
                />
                <label htmlFor="lastName">{employeeForm.lastName}</label>
                <div className="error">{errors.lastName}</div>
              </div>
            </div>
            <div className="text-body">
              <div className="text-input">
                <input
                  type="text"
                  id="contact"
                  name="contact"
                  value={formData.contact}
                  onChange={handleChange}
                  onBlur={handleBlur}
                />
                <label htmlFor="contact">{employeeForm.contact}</label>
                <div className="error">{errors.contact}</div>
              </div>
              <div className="text-input">
                <input
                  type="text"
                  id="email"
                  name="email"
                  value={formData.email}
                  onChange={handleChange}
                  onBlur={handleBlur}
                />
                <label htmlFor="email">{employeeForm.email}</label>
                <div className="error">{errors.email}</div>
              </div>
            </div>
            <div className="text-body">
              <div className="text-input">
                <input
                  type="date"
                  id="date"
                  name="dob"
                  value={formData.dob}
                  onChange={handleChange}
                />
                <label htmlFor="date">{employeeForm.dob}</label>
              </div>
              <div className="text-input">
                <input
                  type="text"
                  id="employeeID"
                  name="empId"
                  value={formData.empId}
                  onChange={handleChange}
                  onBlur={handleBlur}
                />
                <label htmlFor="employeeID">{employeeForm.empId}</label>
                <div className="error">{errors.empId}</div>
              </div>
            </div>
          </div>
          <div className="login-form-button">
            <input type="submit" value="Submit" className="form-button" />
          </div>
        </form>
      </div>
    </div>
  );
};

export default Empdetails;
