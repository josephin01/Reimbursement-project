import React,{useState,useEffect} from "react";
import "./travelForm.scss";
import Colleague from "../../colleague/colleague";
import ColleagueDropdown from "../../colleagueDropdown/colleagueDropdown";
import { travelFormData } from "../../../constants/constant";
import { user } from "../../../assets/mockData/mockData";
import { useSearchParams } from "react-router-dom";
import { useSelector } from "react-redux";

function TravelForm({setAddDataValue,Purpose}) {

  const ColleagueName=useSelector((state)=>state.expenseClaimDetail.colleague);
  const managerName=useSelector((state)=>state.expenseClaimDetail.manager)
  const projectName=useSelector((state)=>state.expenseClaimDetail.project)
  const empID = useSelector((state) => state.user.user.empId)
  const [query, setQuery] = useState("");
  const [results, setResults] = useState([]);
  const [enter, setEnter] = useState(false);
  const [addedColleague, setAddedColleague] = useState([]);
  const [focusedIndex, setFocusedIndex] = useState(-1);
  const [items, setItems] = useState(ColleagueName);
  const [selectedManager, setSelectedManager] = useState("");
  const moment = require("moment");
  const currentDate = moment().format("YYYY-MM-DD");
  const tomorrowDate = moment().add(1, "days").format("YYYY-MM-DD");
  useEffect(()=>{
    setItems(ColleagueName)
  },[ColleagueName])

  const [data, setData] = useState
  ({
    purpose: "",
    "employeeId": empID,
    date: currentDate,
    dateOfTravel: tomorrowDate,
    projectScope: "",
    numberOfPeople: addedColleague.length,
    colleague: [],
    projectId:'',
    managerId:''
  });
   setAddDataValue(data);
  useEffect(() => {
    setFocusedIndex(-1);
  }, [query]);
  useEffect(() => {
    setData({
      ...data,
      colleague: addedColleague,
      numberOfPeople: addedColleague.length,
    });
  }, [addedColleague]);

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

  const handleSearch = (input) => {
    const matchingNames = items.filter((colleague) =>
      colleague.firstName.toLowerCase().includes(input.toLowerCase())
    );
    setResults(matchingNames);
  };

  const handleChange = (e) => {
    const inputValue = e.target.value;
    setQuery(inputValue);
    handleSearch(inputValue);
    setEnter(!!inputValue);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const newdata={purpose:data.purpose, date:data.date, dateOfTravel:data.dateOfTravel,projectScope:data.projectScope,
    numberOfPeople:data.numberOfPeople,projectName:data.projectName,managerName:data.managerName,colleague:data.Colleague}
    setAddDataValue(newdata)
  };

  const handleDropDown = (e) => {
    const selectedValue = e.target.value;
    const project = projectName.find(
      (project) => project.projectName === selectedValue
    );
    if (project) {
      setData({
        ...data,
        projectId: project.id,
      });
    }
  };

  const handleDeleteColleague = (value) => {
    setItems([...items, value]);
    const updatedColleague = addedColleague.filter(
      (colleague) => colleague !== value
    );
    setAddedColleague(updatedColleague);
  };

  const handlePurpose = (e) => {
    const selectedValue = e.target.value;
    const selectedPurpose = Purpose.find((item) => item.purposes === selectedValue);
    if (selectedPurpose) {
      const { id} = selectedPurpose;
      setData({...data,purpose:id})
    }
  };
  const handleManagerDropDown=(e)=>{
    const selectedValue = e.target.value;
    const manager = managerName.find(
      (manager) => manager.managerName === selectedValue
    );
    if (manager) {
      setData({
        ...data,
        managerId: manager.managerId
      });
    }
  }
  

  return (
    <form className="claimFormContainer" onSubmit={handleSubmit}>
      <div className="travelFormField">
        <label>{travelFormData.purpose}:</label>
        <select
          onChange={handlePurpose}
        >
          <option>{travelFormData.selectPurpose}</option>
          {Purpose.map((purpose, index) => (
            <option key={index}>{purpose.purposes}</option>
          ))}
        </select>
      </div>
      <div className="travelFormRow">
        <div className="travelFormField">
          <label>{travelFormData.date}:</label>
          <input
            type="text"
            value={data.date}
            onChange={(e) => setData({ ...data, date: e.target.value })}
            disabled
          />
        </div>
        <div className="travelFormField">
          <label>{travelFormData.dateOfTravel}:</label>
          <input
            type="text"
            onChange={(e) => setData({ ...data, date: e.target.value })}
            value={data.dateOfTravel}
            disabled
          />
        </div>
      </div>
      <div className="travelFormField">
        <label>{travelFormData.colleagueName}:</label>
        <div className="fieldDropdown">
          <div className="colleagueField">
            {addedColleague.map((value, key) => (
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
      </div>
      <div className="travelFormField">
        <label>{travelFormData.noOfPeople}:</label>
        <input type="text" value={data.numberOfPeople} disabled />
      </div>
      <div className="travelFormRow">
        <div className="travelFormField">
          <label>{travelFormData.projectName}:</label>
          <select onChange={handleDropDown} value={data.projectName}>
            <option>{travelFormData.selectProject}</option>
            {projectName.map((project, index) => (
              <option key={index}>{project.projectName}</option>
            ))}
          </select>
        </div>
        <div className="travelFormField">
          <label>{travelFormData.managerName}:</label>
          <select onChange={handleManagerDropDown} value={data.managerName}>
            <option>{travelFormData.selectManager}</option>
            {managerName.map((manager, index) => (
              <option key={index}>{manager.managerName}</option>
            ))}
          </select>
        </div>
      </div>
      <div className="fieldProject">
        <label>{travelFormData.projectScope}:</label>
        <textarea onChange={(e) => setData({ ...data, projectScope: e.target.value })} placeholder="If travel is towards project scope/PI clarity please specify the milestone/task information in detail" />
      </div>
    </form>
  );
}

export default TravelForm;
