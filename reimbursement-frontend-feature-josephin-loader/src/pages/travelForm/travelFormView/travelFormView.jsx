import React, { useState, useContext, useEffect } from "react";
import StatusFlow from "../../../components/statusFlow/statusFlow";
import DisplayForm from "../../../components/forms/displayform/displayform";
import { useLocation } from "react-router-dom";
import { HiOutlinePlusCircle } from "react-icons/hi";
import { v4 as uuidv4 } from "uuid";
import ExpenseClaimForm from "../../../components/forms/expenseClaim/travelExpenseClaimForm";
import { AppContext } from "../../../context/context";
import "./travelFormView.scss";
import {
  collegueData,
  expenseTypeData,
  saveAndSend,
} from "../../../action/api/Api_config";
import { travelFormHeaders } from "../../../constants/constant";
import { useDispatch, useSelector } from "react-redux";
import { travelFormViewData } from "../../../constants/constant";
import axios from "axios";
import { clearExpense } from "../../../redux/slice/expenseSlice";
import Button from "../../../components/buttons/buttons";
import TravelExpenseClaimForm from "../../../components/forms/expenseClaim/travelExpenseClaimForm";

function travelFormView() {
  const dispatch = useDispatch();
  const Arr = [];
  const location = useLocation();
  const { status, travelId } = location.state;
  const [expensesList, setExpensesList] = useState([]);
  const { viewData } = useContext(AppContext);
  const [viewDataValue, setViewDataValue] = viewData;
  const [expenseType, setExpenseTpe] = useState();
  const [colleague, setCollegue] = useState();
  const [success, setSuccess] = useState(false);
  const [failure,setFailure] = useState(false)
  const moment = require("moment");
  const applyDate = moment().format("YYYY-MM-DD");
  // const todayDate = new Date();

  

  const handleAddExpense = () => {
    const newExpense = {
      id: uuidv4(),
      title: `Expense ${expensesList.length + 1}`,
      expenseType: {},
      expenseAmount: "",
      colleague: [],
      expenseDescription: "",
      expenseDate: "",
      applyDate: applyDate,
      bills: [],
    };
    setExpensesList([...expensesList, newExpense]);
  };

  const handleDeleteExpense = (id) => {
    setExpensesList((prevExpenses) =>
      prevExpenses.filter((expense) => expense.id !== id)
    );
    setExpensesList((prevExpenses) =>
      prevExpenses.map((expense, index) => ({
        ...expense,
        title: `Expense ${index + 1}`,
      }))
    );
  };
  useEffect(() => {
    expenseTypeData(setExpenseTpe);
  }, []);

  useEffect(() => {
    collegueData(setCollegue);
  }, []);

  const handleAddExpenseData = (expenseData) => {
    setExpensesList((prevExpenses) => [...prevExpenses, expenseData]);
  };
  const handleSubmit = () => {
    saveAndSend(travelId,expensesList)
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
    // try {
    //   saveAndSend(travelId, expensesList);
    //   setSuccessMessage("Expenses Submitted Successfully!");
    //   setTimeout(() => {
    //     setSuccessMessage("");
    //   }, 3000);
    // } catch (error) {
    //   console.error("Error submitting expenses:", error);
    // }
  };

  const handleExpenseList = (expense) => {
    setExpensesList((prevExpenses) =>
      prevExpenses.map((newExpense) =>
        newExpense.id === expense.id
          ? { ...newExpense, ...expense }
          : newExpense
      )
    );
  };


  const [totalExpenseAmount, setTotalExpenseAmount] = useState(0);

  useEffect(() => {
    const totalAmount = expensesList.reduce((total, expense) => {
      const amount = parseInt(expense?.expenseAmount || 0);
      return total + amount;
    }, 0);

    setTotalExpenseAmount(totalAmount);
  }, [expensesList]);


  return (
    <div>
       {success?<div className="successMessage1">
        Expenses Added Successfully
      </div>:failure?<div className="errorMessage">
        Some error occured. Try again
      </div>:null}
        <div className="statusCards">
          <StatusFlow travelData={viewDataValue} status={status} />
        </div>
        <div className="travelFormDetails">
          <h3 className="travelFormDetailsHeading">
            {travelFormViewData.travelForm}
          </h3>
          <DisplayForm Data={viewDataValue} headers={travelFormHeaders} />
        </div>
        {status == "FORM APPROVED" && (
          <div>
            {expensesList.map((expense) => (
              <div key={expense.id} className="expenseContainer">
                <TravelExpenseClaimForm
                  expense={expense}
                  expensesList={expensesList}
                  setExpensesList={setExpensesList}
                  onDeleteExpense={handleDeleteExpense}
                  expenseTypes={expenseType}
                  ColleagueName={colleague}
                  addExpenseData={handleAddExpenseData}
                  handleExpenseList={handleExpenseList}
                />
              </div>
            ))}
            <div className="totalAmountDetails">
              <div className="addExpense">
                <HiOutlinePlusCircle />
                <span onClick={handleAddExpense} className="addExpenseLink">
                  {" "}
                  {travelFormViewData.addExpense}
                </span>
              </div>
              <div className="totalAmount">
                {travelFormViewData.totalAmount}: {totalExpenseAmount}
              </div>
            </div>

            <div className="saveAndSendButton">
              <div className="saveAndSend" onClick={handleSubmit}>
                <Button Data={"Save and Send"} />
              </div>
            </div>
          </div>
        )}
        {status == "FORM REJECTED" && (
          <div className="totalAmountDetails">
            <div className="rejected">{travelFormViewData.rejected}!</div>
          </div>
        )}
    </div>
  );
}

export default travelFormView;