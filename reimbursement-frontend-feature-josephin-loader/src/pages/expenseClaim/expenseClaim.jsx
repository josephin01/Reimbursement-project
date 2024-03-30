import React, { useEffect, useState } from "react";
import Button from "../../components/buttons/buttons";
import Displayform from "../../components/forms/displayform/displayform";
import ExpenseClaimForm from "../../components/forms/expenseClaim/expenseClaimForm";
import {
  collegueData,
  fetchExpenseClaimData,
  fetchExpenseViewAPI,
  expenseFormSubmitAPI,
} from "../../action/api/Api_config";
import { displayFormData, expenseClaimData } from "../../constants/constant";
import Search from "../../components/search/search";
import "./expenseClaim.scss";

function ExpenseClaim() {
  const [showForm, setShowForm] = useState(false);
  const [expensesList, setExpensesList] = useState([]);
  const [showNew, setShowNew] = useState(false);
  const [tableData, setTableData] = useState([]);
  const [data, setData] = useState([]);
  const [expenseType, setExpenseTpe] = useState();
  const [input, setInput] = useState({});
  const [colleague, setColleague] = useState();
  const [collegue, setCollegue] = useState([]);
  const [successMessage, setSuccessMessage] = useState("");
  const [loading,setLoading] = useState(true)

  const handleForm = () => {
    setShowNew(true);
  };
  useEffect(() => {
    collegueData(setColleague);
  }, []);

  // const handleSubmitForm =() => {
  //   expenseFormSubmitAPI(input,setShowNew);
  // };
  const handleSubmitForm = () => {
    expenseFormSubmitAPI(input, () => {
      setSuccessMessage("Expense Claim Added Successfully!");
      setTimeout(() => {
        setSuccessMessage(false);
        fetchExpenseClaimData(setTableData);
        setShowNew(false);
      }, 3000);
      
    });
  };

  const handleAddExpenseData = (expenseData) => {
    setExpensesList((prevExpenses) => [...prevExpenses, expenseData]);
  };

  // useEffect(() => {
  //   fetchExpenseClaimData(setTableData);
  // }, []);
  useEffect(() => {
    setLoading(true); 
    fetchExpenseClaimData((data) => {
      setTableData(data);
      setLoading(false); 
    });
  }, []);

  const handleExpenseView = (row) => {
    fetchExpenseViewAPI(row.id, setData);
  };

  useEffect(() => {
    collegueData(setCollegue);
  }, []);

  return (
    <div>
      {/* {loading? (
        <div>Loading....</div>
      ):showForm ? (
        <Displayform Data={data} headers={displayFormData} />
      ) : showNew ? (
        <div>
          {successMessage && (
            <div className={`successMessage ${successMessage === "Expense Claim Added Successfully!" ? "show" : ""}`}>
              {successMessage}
            </div>
          )}
          <ExpenseClaimForm setInput={setInput} ColleagueName={collegue} />
          <div>
            {expensesList.map((expense) => (
              <div key={expense.id} className="expenseContainer">
                <ExpenseClaimForm
                  id={expense.id}
                  title={expense.title}
                  onDeleteExpense={() => handleDeleteExpense(expense.id)}
                  expenseTypes={expenseType}
                  ColleagueName={colleague}
                  addExpenseData={handleAddExpenseData}
                  setInput={setInput}
                />
              </div>
            ))}
          </div>
          <div onClick={handleSubmitForm}>
            <Button Data="Save & Send" />
          </div>
        </div>
      ) : (
        <div>
          <div onClick={handleForm}>
            <Button Data="Add Claim" />
          </div>

          <Search
            headers={expenseClaimData}
            Data={tableData}
            isView={true}
            fetchView={handleExpenseView}
            setShowRow={setShowForm}
            searchBase="expenseType"
          />
        </div>
      )} */}
    </div>
  );
}

export default ExpenseClaim;
