

import React, { useEffect, useState,useRef } from "react";
import { MdOutlineAddCircleOutline } from "react-icons/md";
import AddBills from "../../bill/addBill/addBill";
import Colleague from "../../colleague/colleague";
import "./expenseClaim.scss";
import { storage } from "../../../firebase";
import {
  ref,
  uploadBytes,
  getDownloadURL,
  deleteObject,
} from "firebase/storage";
import { v4 } from "uuid";
import ColleagueDropdown from "../../colleagueDropdown/colleagueDropdown";
import { SlClose } from "react-icons/sl";
import { useDispatch } from "react-redux";
import { expenseClaim } from "../../../constants/constant";
import { useSelector } from "react-redux";

function TravelExpenseClaimForm({
  expense,
  expensesList,
  setExpensesList,
  onDeleteExpense,
  addExpenseData,
}) {
  const ColleagueName=useSelector((state)=>state.expenseClaimDetail.colleague)
  const expenseTypes=useSelector((state)=>state.expenseClaimDetail.expenseType)
  const myRef=useRef(null)
  const [query, setQuery] = useState("");
  const [results, setResults] = useState([]);
  const [enter, setEnter] = useState(false);
  const [addedColleague, setAddedColleague] = useState([]);
  const [focusedIndex, setFocusedIndex] = useState(-1);
  const [items, setItems] = useState([]);
  const moment = require("moment");
  const applyDate = moment().format("YYYY-MM-DD");
  const [expenseData, setExpenseData] = useState({
    id: expense.id,
    title: expense.title,
    expenseType: { id: "", expenses: "" },
    expenseAmount: "",
    colleague: [],
    expenseDescription: "",
    expenseDate: "",
    applyDate: applyDate,
    bills: [],
  });

  useEffect(()=>{
    setItems(ColleagueName)
  },[ColleagueName])

  useEffect(()=>{
    const updatedExpensesList = expensesList.map((expense) =>
    expense.id === expenseData.id ? expenseData : expense
  );
  setExpensesList(updatedExpensesList);
}, [expenseData]);

  const [selectedFile, setSelectedFile] = useState(false);  
  const [bill, setBill] = useState([]);

  useEffect(() => {
    setExpenseData({ ...expenseData, bills: bill });
  }, [bill]);
  useEffect(() => {
    setFocusedIndex(-1);
  }, [query]);
  useEffect(() => {
    setExpenseData({ ...expenseData, colleague: addedColleague });
  }, [addedColleague]);

  const handleChange = (e) => {
    const inputValue = e.target.value;
    setQuery(inputValue);
    handleSearch(inputValue);
    setEnter(!!inputValue);
  };
  const handleExpenseTypeChange = (e) => {
    const selectedExpenseType = e.target.value;
    const selectedExpense = expenseTypes.find(expense => expense.expenses === selectedExpenseType);
    setExpenseData({ ...expenseData, expenseType: { id: selectedExpense.id, expenses: selectedExpenseType } });
  };

  const handleSearch = (input) => {
    const matchingNames = items.filter((colleague) =>
      colleague.firstName.toLowerCase().includes(input.toLowerCase())
    );

    setResults(matchingNames);
  };

  const handleKeyDown = (e) => {
    if (e.key === "ArrowDown") {
      e.preventDefault();
      setFocusedIndex((prevIndex) =>
        prevIndex < results.length - 1 ? prevIndex + 1 : prevIndex
      );
    } else if (e.key === "ArrowUp") {
      e.preventDefault();
      setFocusedIndex((prevIndex) =>
        prevIndex > 0 ? prevIndex - 1 : prevIndex
      );
    } else if (e.key === "Enter" && focusedIndex !== -1) {
      handleOptionClick(results[focusedIndex]);
    }
  };

  const handleOptionClick = (option) => {
    setQuery("");
    const updatedList = [...items];
    const index = updatedList.indexOf(option);
    if (index !== -1) {
      updatedList.splice(index, 1);
      setItems(updatedList);
      setResults([]);
      setEnter(false);
      setAddedColleague((prevValue) => [...prevValue, option]);
    }
  };

  const handleDeleteColleague = (value) => {
    setItems([...items, value]);
    const updatedColleague = addedColleague.filter(
      (colleague) => colleague !== value
    );
    setAddedColleague(updatedColleague);
  };

  const handleFileChange = async (event) => {
    const file = event.target.files[0];
    setSelectedFile(true);
    handleUrl(file);
  };

  const handleUrl = async (file) => {
    const imageRef = ref(storage, `images/${file.name + v4()}`);
    try {
      await uploadBytes(imageRef, file);
      const url = await getDownloadURL(imageRef);
      const billData = {
        billType: file.type,
        billsUrl: url,
        billName: file.name,
      };
      setBill((prevData) => [...prevData, billData]);
    } catch (error) {
      alert("There is a problem in uploading your file. Try again later.");
      console.error("Error uploading file:", error);
    }
  };

  const handleDeleteBill = async (url) => {
    try {
      const fileRef = ref(storage, url);
      await deleteObject(fileRef);
      const updatedBill = bill.filter((deleteBill) => deleteBill.billsUrl !== url);
      setBill(updatedBill);
    } catch (error) {
      alert("There is a problem in deleting your file.Try again later");
      console.error("Error deleting file:", error);
    }
  };

  
  const handleSubmit = (e) => {
    e.preventDefault();
    addExpenseData(expenseData);
  };

  useEffect(() => {
    const handleClickOutside = (event) => {
      if (myRef.current && !myRef.current.contains(event.target)) {
        setEnter(false)
        setResults([])
      }
    };
    document.addEventListener('mousedown', handleClickOutside);
    return () => {
      document.removeEventListener('mousedown', handleClickOutside);
    };
  }, []);

  return (
    <form className="ExpenseClaimContainer" onSubmit={handleSubmit}>
      <div className="titleForm">
        <h3>{expense.title} </h3>
        <SlClose onClick={() => onDeleteExpense(expense.id)} className="closeView" />
        </div>
        <div className="expenseRow">
        <div className="ExpenseField">
          <label>{expenseClaim.expenseType}:</label>
          <select
            value={expenseData.expenseType.expenses}
            onChange={handleExpenseTypeChange}
          >
          
            <option>{expenseClaim.selectExpenseType}</option>
            {expenseTypes.map((value, index) => (
              <option key={index}>{value.expenses}</option>
            ))}
          </select>
        </div>
        <div className="ExpenseField">
          <label>{expenseClaim.expenseAmount}:</label>
          <input
            type="text"
            value={expenseData.expenseAmount}
            onChange={(e) =>
              setExpenseData({ ...expenseData, expenseAmount: e.target.value })
              
            }
          />
        </div>
        <div className="ExpenseField">
          <label>{expenseClaim.expenseDate}:</label>
          <input
            type="date"
            value={expenseData.expenseDate}
            onChange={(e) =>
              setExpenseData({ ...expenseData, expenseDate: e.target.value })
            }
          />
        </div>
        </div>
      <div className="ExpenseField">
        <label>{expenseClaim.colleagueName}:</label>
        <div className="fieldDropdown">
          <div className="colleagueField">
            {addedColleague && addedColleague.map((value) => (
              <Colleague
                value={value}
                handleDeleteColleague={handleDeleteColleague}
              />
            ))}
            <input
              className="colleagueInput"
              type="text"
              value={query}
              onChange={handleChange}
              onKeyDown={handleKeyDown}
            />
          </div>
            {enter && (
              <ul className="dropdownContainer" ref={myRef}>
                {results.map((result, index) => (
                  <li
                    key={index}
                    onClick={() => handleOptionClick(result)}
                    className={index === focusedIndex ? "focused" : ""}
                  >
                    <ColleagueDropdown value={result} />
                  </li>
                ))}
              </ul>
            )}
        </div>
                  
      </div>
      <div className="fieldRemark">
        <label>{expenseClaim.remarks}:</label>
        <textarea
          value={expenseData.expenseDescription}
          onChange={(e) =>
            setExpenseData({
              ...expenseData,
              expenseDescription: e.target.value,
            })
          }
        />
      </div>
      <div className="BillAdd">
        {selectedFile
          ? bill.map((value, index) => (
              <AddBills bill={value} deleteBill={handleDeleteBill} />
            ))
          : null}
        <label htmlFor={`AddBillInput ${expenseData.id}`}>
        <MdOutlineAddCircleOutline /> {expenseClaim.addBill}
        </label>
        <input style={{background:"red",height:"max-height",width:"max-width"}} type="file" onChange={handleFileChange} id={`AddBillInput ${expenseData.id}`}/>
      </div>
      
    </form>
    
  );
}

export default TravelExpenseClaimForm;
