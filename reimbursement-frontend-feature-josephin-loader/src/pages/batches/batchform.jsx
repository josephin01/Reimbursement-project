import React, { useState, useEffect } from "react";
import Button from "../../components/buttons/buttons";
import "./batchform.scss";
import { useNavigate, useSearchParams } from "react-router-dom";
import { batchFormData } from "../../constants/constant";
import { handleViewFormAPi } from "../../action/api/Api_config";
import { useSelector } from "react-redux";
import Select from "react-select";
import { employeeNameReport } from "../../action/api/Api_config";

function BatchForm() {
  const navigate = useNavigate();
  const [data, setData] = useState([]);
  const [expense, setExpense] = useState([]);
  const user = useSelector((state) => state.user.user);
  const [employeeName, setEmployeeName] = useState([]);
  const [allSelectedEmployee, setAllSelectedEmployee] = useState(false);
  const [passData, setPassData] = useState({
    fromDate: "",
    toDate: "",
    references: "",
    employeeDetails: {
      empId:"",
      firstName: "",
    },
  });

  const calculateMinToDate = () => {
    if (passData.fromDate) {
      const minDate = new Date(passData.fromDate);
      minDate.setDate(minDate.getDate() + 1);
      const minDateISO = minDate.toISOString().split("T")[0];
      return minDateISO;
    }
    return "";
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    if (name === "firstName") {
      setPassData({
        ...passData,
        employeeDetails: {
          ...passData.employeeDetails,
          firstName: value,
        },
      });
    } else if (name.startsWith("employeeDetails.")) {
      const field = name.split(".")[1];
      setPassData({
        ...passData,
        employeeDetails: {
          ...passData.employeeDetails,
          [field]: value,
        },
      });
    } else {
      setPassData({ ...passData, [name]: value });
    }
  };

  useEffect(() => {
    employeeNameReport(setEmployeeName);
  }, []);

  const handleViewForm = () => {
    handleViewFormAPi(setData, setExpense, navigate, passData);
  };
  console.log(employeeName,"emppppp")

  const employeeNameList = [
    { label: "All", value: "all" },
    ...employeeName.map((employee) => ({
      label: `${employee.firstName} ${employee.lastName}`,
      value: employee.empId,
      firstName:employee.firstName
      
    })),
  ];
  console.log(employeeNameList,"llllllll")

  // const handleClickEmployee = (selectedOptions) => {
  //   console.log(selectedOptions);
  //   const isAllSelected = selectedOptions?.some(
  //     (option) => option.value === "all"
  //   );

  //   if (isAllSelected) {
  //     setAllSelectedEmployee(true);
  //     setPassData({ ...passData, empId: [] });
  //   } else {
  //     setAllSelectedEmployee(false);
  //     const selectedEmpIds = selectedOptions
  //       ?.filter((option) => option.value !== "all")
  //       ?.map((option) => option.value);
  //     setPassData({
  //       ...passData,
  //       empId: selectedEmpIds,

  //     });
  //   }
  // };
  // const handleClickEmployee = (selectedOptions) => {
  //   console.log(selectedOptions);
  
  //   const selectedEmployees = selectedOptions.map((option) => ({
  //     empId: option.value,
  //     firstName: option.firstName, // Rename firstName to first_name
  //   }));
  
  //   setPassData({
  //     ...passData,
  //     employeeDetails: {
  //       ...passData.employeeDetails,
  //       empId: selectedEmployees.map((employee) => employee.empId),
  //       firstName: selectedEmployees.map((employee) => employee.firstName), // Include first_name
  //     },
  //   });
  // };
  const handleClickEmployee = (selectedOptions) => {
    if (selectedOptions.length > 0) {
      const selectedEmployee = selectedOptions[0];
      setPassData({
        ...passData,
        employeeDetails: {
          empId: selectedEmployee.value,
          firstName: selectedEmployee.firstName,
        },
      });
    } else {
      // Handle case when no employee is selected
      setPassData({
        ...passData,
        employeeDetails: {
          empId: "",
          firstName: "",
        },
      });
    }
  };
  
  
  
  

  return (
    <div>
      <h2>Add Batch</h2>

      <div className="body">
        <form className="formContent">
          <div className="row-content">
            <div className="content">
              <label htmlFor="fromDate">{batchFormData.From}</label>
              <input
                type="date"
                id="fromDate"
                className="inputs"
                value={passData.fromDate}
                name="fromDate"
                onChange={handleChange}
              />
            </div>
            <div className="content">
              <label htmlFor="toDate">{batchFormData.To}</label>
              <input
                type="date"
                id="toDate"
                className="inputs"
                min={calculateMinToDate()}
                value={passData.toDate}
                name="toDate"
                onChange={handleChange}
              />
            </div>
          </div>
          <div className="row-content">
            <div className="content">
              <label>Employee Name</label>
        <Select
          className="select"
          options={employeeNameList}
          onChange={(selectedOptions) => handleClickEmployee(selectedOptions)}
          isMulti={true}
          placeholder="Select Employee Name"
          isOptionDisabled={(option) =>
            allSelectedEmployee && option.value !== "all"
          }
        />
            </div>
            <div className="content">
              <label htmlFor="remarks">{batchFormData.Remarks}</label>
              <input
                type="text"
                id="remarks"
                className="inputs"
                value={passData.references}
                name="references"
                onChange={handleChange}
              />
            </div>
          </div>
        </form>
        <div onClick={handleViewForm}>
          <Button Data="Club" />
        </div>
      </div>
    </div>
  );
}

export default BatchForm;
