import React, { useEffect, useState } from "react";
import "../people/people.scss";
import { fetchEmployees, employeeDeleteAPI, fetchData } from "../../action/api/Api_config";
import { peopleTable } from "../../constants/constant";
import Edit from "../../components/Edit/edit";
import Search from "../../components/search/search";
import Delete1 from "../../components/delete/delete";

function People() {
  const [employeeData, setEmployeeData] = useState([]);
  const [editState, setEditState] = useState(false);
  const [ShowPopUp, setShowPopUp] = useState(false);
  const [showRow, setShowRow] = useState(false);
  const [peopleData, setPeopleData] = useState(null);
  const [deleteID, setDeleteID] = useState();
  const [loading, setLoading] = useState(true);
  const [successMessage,setSuccessMessage]=useState("")
  const [failure,setFailure]=useState(false)

  useEffect(() => {
    const fetchData = async () => {
      const data = await fetchEmployees(setEmployeeData);
    };
    fetchData();
  }, []);

  const reloadPeopleData =() =>{
    fetchEmployees(setEmployeeData);
  }

  const handleDeleteClick = (row) => {
    setShowPopUp(true);
    setDeleteID(row.empId);
  };

  const handlePeopleEdit = (row) => {
    setEditState(true);
    setPeopleData(row);
  };

  const handleEditSave = (editedData) => {
    setEmployeeData(
      employeeData.map((employee) =>
        employee.empId === editedData.empId ? editedData : employee
      )
    );
    setEditState(false);
    setSuccessMessage("Employee Updated Successfully!");
    reloadPeopleData()
    setTimeout(() => {
     setSuccessMessage("");
  }, 3000); 
  };
 

  return (
    <div className="admin-people">
      {ShowPopUp && (
        <div className="deletePopUP">
          <Delete1
            title="Employee"
            deleteAPI={employeeDeleteAPI}
            deleteID={deleteID}
            setShowPopup={setShowPopUp}
            message = {"Employee Deleted Successfully!"}
            setSuccessMessage={setSuccessMessage}
            reloadData = {reloadPeopleData}
          />
        </div>
      )}
      <div className="employee-table">
        <h1>People</h1>

        {successMessage && (
        <div
          className={`successMessage ${
            (successMessage === "Employee Updated Successfully!" ? "show" : "") ||
            (successMessage === "Employee Deleted Successfully!"? "show":"")
          }`}
        >
          {successMessage}
        </div>
      )}

        {employeeData && (
          <Search
            headers={peopleTable}
            Data={employeeData}
            isView={false}
            setShowRow={setShowRow}
            onDelete={handleDeleteClick}
            onEdit={handlePeopleEdit}
            searchBase="employeeName"
          />
        )}
      </div>
      <div className="editPeople">
        {editState && (
          <Edit
            employeeData={peopleData}
            setEditState={setEditState}
            onSave={handleEditSave}
          />
        )}
      </div>
    </div>
  );
}

export default People;
