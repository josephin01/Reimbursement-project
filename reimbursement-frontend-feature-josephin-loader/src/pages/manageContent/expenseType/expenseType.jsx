import "../expenseType/expenseType.scss";
import React, { useEffect, useState } from "react";
import Table from "../../../components/table/table";
import { GiCancel } from "react-icons/gi";
import Button from "../../../components/buttons/buttons";
import { expenseTypeTable, expenseTypeData } from "../../../constants/constant";
import Search from "../../../components/search/search";
import Delete1 from "../../../components/delete/delete";
import {
  expenseTypeDetailsAPI,
  expenseFormTypeAPI,
  expenseFormEditAPI,
  expenseFormDetailsAddAPI,
  expenseFormDeleteAPI,
} from "../../../action/api/Api_config";
import { useSelector } from "react-redux";

const expenseType = () => {
  const [expenseType, setExpenseType] = useState();
  const [formType, setFormType] = useState();
  const [isAdd, setISAdd] = useState(false);
  const [addData, setAddData] = useState();
  const [edit, setEdit] = useState();
  const [editedData, setEditedData] = useState({});
  const [deleteID,setDeleteID] = useState();
  const [popUp,setPopUp] = useState(false);
  const [successMessage,setSuccessMessage]=useState("");
  const expenseTypeDetails=useSelector((state)=>state.expenseClaimDetail.expenseType);
  
  useEffect(() => {
    expenseTypeDetailsAPI(setExpenseType);
    expenseFormTypeAPI(setFormType);
  }, []);

  const handleIcon = () => {
    setISAdd(false);
    setEdit(false);
  };

  const reloadExpenseTypeData = () =>{
    expenseTypeDetailsAPI(setExpenseType);
  }
  const handleChnage = (e) => {
    const { name, value } = e.target;
    setEditedData((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    await expenseFormEditAPI(editedData);
    setSuccessMessage("Expense type updated successfully");
    reloadExpenseTypeData()
    setTimeout(() => {
      setSuccessMessage("");
      setEdit(false)
    }, 3000);
  };
  const addExpense = (e) => {
    e.preventDefault();
    expenseFormDetailsAddAPI(addData);
    setISAdd(false);
    setSuccessMessage("Expense type added successfully");
    setTimeout(() => {
      setSuccessMessage("");
      reloadExpenseTypeData();
    }, 3000);
  }

  const handleExpense = (e) => {
    const { name, value } = e.target;
    setAddData((prev) => ({ ...prev, [name]: value }));
  };

  const handleAdd = () => {
    setISAdd(true);
  };
  const handleEdit = (row) => {
   setEdit(row);
   setEditedData((prev) => ({...prev,['id']: row.id}))
  };

  const handleDeleteClick = (row) => {
    setPopUp(true);
    setDeleteID(row.id);
  };


  
  return (
    <div className="expense-type">
      { popUp && <div>
        <Delete1 
          title='Expense'  
          deleteAPI={expenseFormDeleteAPI} 
          deleteID={deleteID} 
          setShowPopup = {setPopUp} 
          message={"Expense Type Deleted Successfully!"}
          setSuccessMessage={setSuccessMessage}
          reloadData={reloadExpenseTypeData}
          />
        </div>}
      <div className="expense-header">
        <h3>Expense Type</h3>
        <div onClick={handleAdd}>
          <Button Data="Add Expense" />
        </div>
        {successMessage && (
  <div className={`successMessage ${successMessage ? 'show' : ''}`}>
    {successMessage}
  </div>
)}
      </div>
      <div className="add-expense">
        {isAdd && (
          <div className="edit-wrapper">
            <form action="" className="expenseEditForm" onSubmit={addExpense}>
              <GiCancel
                className="expenseEditFormCancelIcon"
                onClick={handleIcon}
              />
              <div className="expenseEditFormInputs">
                <label>{expenseTypeData.Expenses}</label>{" "}
                <input
                  type="text"
                  name="expenses"
                  className="expenseEditFormInput"
                  onChange={handleExpense}
                />
              </div>
              <div className="expenseEditFormInputs">
                <label>{expenseTypeData.FormType}</label>
                <select
                  className="expenseEditFormSelect"
                  onChange={handleExpense}
                  name="formType"
                >
                  {formType && formType.data.map((role, index) => (
                    <option key={role.value} value={role.value}>
                      {role}
                    </option>
                  ))}
                </select>
              </div>
              <div>
                <Button Data="Submit" />
              </div>
            </form>
          </div>
        )}
      </div>

      <div className="expense-table">
        {expenseType && (
          <Table
            headers={expenseTypeTable}
            Data={expenseTypeDetails}
            isView={false}
            setShowRow={setEdit}
            onDelete={handleDeleteClick}
            onEdit={handleEdit}
            searchBase = "expenses"
          />
        )}
      </div>

      {edit && (
        <div className="edit-wrapper">
          <form action="" className="expenseEditForm" onSubmit={handleSubmit}>
            <GiCancel
              className="expenseEditFormCancelIcon"
              onClick={handleIcon}
            />
            <div className="expenseEditFormInputs">
              <label>{expenseTypeData.Expenses}</label>{" "}
              <input
                type="text"
                name="expenses"
                className="expenseEditFormInput"
                defaultValue={edit.expenses}
                onChange={handleChnage}
              />
            </div>
            <div className="expenseEditFormInputs">
              <label>{expenseTypeData.FormType}</label>
              <select
                className="expenseEditFormSelect"
                defaultValue={edit.formType}
                onChange={handleChnage}
                name="formType"
              >
                {formType.data.map((role) => (
                  <option key={role.value} value={role.value}>
                    {role}
                  </option>
                ))}
              </select>
            </div>
            <div className="expenseButtons">
              <div className="expenseButton">
                <Button Data="Submit" />
              </div>
              <div className="expenseButton" onClick={() => handleIcon()}></div>
            </div>
          </form>
        </div>
      )}
    </div>
    

  );
};

export default expenseType;
