import React, { useEffect, useState } from "react";
import { MdOutlineAddCircleOutline } from "react-icons/md";
import { ColleagueName, ExpenseType } from "../../../assets/mockData/mockData";
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
import { useSelector } from "react-redux";


function ExpenseClaimForm({id,setExpense}) {
  const ColleagueName=useSelector((state)=>state.expenseClaimDetail.colleague)
  const manager=useSelector((state)=>state.expenseClaimDetail.manager)
  const project=useSelector((state)=>state.expenseClaimDetail.project)
  const expenseType=useSelector((state)=>state.expenseClaimDetail.expenseType)
  const userData  = useSelector((state) => state.user.user);
  const currentDate = new Date();
  const formattedDate = currentDate.toISOString().split('T')[0]; 
  
  const [query, setQuery] = useState("");
  const [results, setResults] = useState([]);
  const [enter, setEnter] = useState(false);
  const [addedColleague, setAddedColleague] = useState([]);
  const [focusedIndex, setFocusedIndex] = useState(-1);
  const [items, setItems] = useState(ColleagueName);
  const [expenseData, setExpenseData] = useState({
    applyDate:formattedDate,
    employeeId:userData.empId,

  });
  const [collegue,setCollegue] = useState();  
  const [selectedFile, setSelectedFile] = useState(false);
  const [bill, setBill] = useState([]);

  useEffect(()=>{
    setExpense(expenseData)
  },[expenseData])


  useEffect(() => {
    setExpenseData({ ...expenseData, billsDto: bill });
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
      const billData = { billType: file.type, billsUrl: url, billName: file.name };
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
      const updatedBill = bill.filter((deleteBill) => deleteBill.url !== url);
      setBill(updatedBill);
    } catch (error) {
      alert("There is a problem in deleting your file.Try again later");
      console.error("Error deleting file:", error);
    }
  };


  const handleOnChange = (e) => {

    const { name,value } = e.target;
    setExpenseData({...expenseData,[name]:value});
    
  }

  return (
    <div>
      <div className="expenseRowOne">
      <div className="expenseRow">
        <div className="ProjectsField">
          <label>Manager Name:</label>
          <select onChange={handleOnChange} name='managers'>
            <option>Select Manager</option>
            {manager && manager.map((value, index) => (

              <option key={index} value={value.managerId}>{value.managerName}</option>
            ))}
          </select>
        </div>
      </div>
      <div className="expenseRow">
        <div className="ProjectsField">
          <label>Project: </label>
          <select  onChange={handleOnChange} name='projects'>
          <option>Select Project</option>
          {project && project.map((value,index) => (
              <option value={value.id}>{value.projectName}</option>
            ))}
          </select>
        </div>
      </div>
      </div>
    <form className="ExpenseClaimContainer" id={id}>
      <div className="titleForm">
      <h3>Expense Claim</h3>
      {/* <SlClose onClick={() => onDeleteExpense(id)} className="closeView" /> */}
      </div>
      
      

      <div className="expenseRow">
        <div className="ExpenseField">
          <label>Expense Type:</label>
          <select onChange={handleOnChange} name='expenseType'>
          <option>Select ExpenseType</option>
            {expenseType && expenseType.map((value, index) => (
              <option value={value.id}>{value.expenses}</option>
            ))}
          </select>
        </div>
        <div className="ExpenseField">
          <label>Expense Amount:</label>
          <input
            type="text"
            name="expenseAmount"
            onChange={handleOnChange}
          />
        </div>
        <div className="ExpenseField">
          <label>Expense Date:</label>
          <input
            type="date"
            name="expenseDate"
            onChange={handleOnChange}
          />
        </div>
      </div>
      <div className="ExpenseField">
        <label>Colleague Name:</label>
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
          <div className="dropdownNames">

            {enter && (
              <ul className="dropdownContainer">
                {results.map((result, index) => (
                  <li
                    name = "colleague"
                    key={index}
                    onClick={() => handleOptionClick(result)}
                    className={index === focusedIndex ? "focused" : ""}
                    onChange={handleOnChange}
                  >
                    <ColleagueDropdown  value={result} />
                  </li>
                ))}
              </ul>
            )}
          </div>
        </div>
                  
      </div>
      <div className="fieldRemark">
        <label>Remarks:</label>
        <textarea
          name="expenseDescription"
          onChange={handleOnChange}
        />
      </div>
      <div className="BillAdd">
        {selectedFile
          ? bill.map((value, index) => (
              <AddBills bill={value} deleteBill={handleDeleteBill} />
            ))
          : null}
        <label for="AddBillInput">
          <MdOutlineAddCircleOutline /> Add Bill  
        </label>
        <input type="file" name="billsDto" onChange={handleFileChange} id="AddBillInput" />
      </div>
    </form>
    </div>
    
  );
}

export default ExpenseClaimForm;