import React, { useContext,useState } from "react";
import "./expenseView.scss";
import StatusFlow from "../../components/statusFlow/statusFlow";
import DisplayForm from "../../components/forms/displayform/displayform";
import Button from "../../components/buttons/buttons"
import { ExpenseData} from "../../assets/mockData/mockData";
import { AppContext } from "../../context/context";
import { travelFormHeaders, expenseHeaders } from "../../constants/constant";
import {fetchStatusChange} from "../../action/api/Api_config"
import { useSelector } from "react-redux";
// import e from "cors";

function ExpenseView() {
  const { viewData,selectedList } = useContext(AppContext);
  const [viewDataValue, setViewDataValue] = viewData;
  const [selectedListValue, setSelectedListValue] = selectedList;
  const [isRemarks,setIsRemarks]=useState(false)
  const [remarks,setRemarks]=useState("")
  const [status,setStatus]=useState("")
  const expenseList = viewDataValue.expensesList;
  const { expensesList, ...travelFormData } = viewDataValue;
  const user = useSelector((state) => state.user.user)

  const handleStatus=(status)=>{
    fetchStatusChange(status,remarks,selectedListValue.id)
  }

  const handleClick=(status)=>{
    setStatus(status)
    if(status == "Approve"){
      handleStatus()
    }
    setIsRemarks(!isRemarks)
  }

  const handleApprove = () =>{
    setStatus("ADMIN_APPROVED")
    fetchStatusChange("ADMIN_APPROVED",remarks,selectedListValue.id)

  }
  const handleReject=()=>{
    setIsRemarks(true)
  }
  const handleRemarks = () =>{
    fetchStatusChange("ADMIN_REJECTED",remarks,selectedListValue.id)
    handleStatus()
  }
  
  return (
    <div className="form1Container">
      <div>
        <StatusFlow
          status={selectedListValue.expenseStatus}
          travelData={travelFormData}
          expenseData={expenseList}
        />
      </div>
      <div>
        <div className="ViewTitle">Travel Form</div>
        <div>
          <DisplayForm Data={travelFormData} headers={travelFormHeaders} />
        </div>
      </div>
      <div>
        <div className="ViewTitle">Expense Claim</div>
        <div className="expenseDetails">
          {expenseList.map((expense, key) => (
            <DisplayForm Data={expense} headers={expenseHeaders} />
          ))}
        </div>
      </div>
      <div className="totalAmount">Total Amount:{selectedListValue.expenseAmount}</div>
      {user.role==1 && selectedListValue.expenseStatus=="PENDING" || selectedListValue.expenseStatus=="MANAGER_APPROVED"?
      <div className="ExpenseViewButton">
      <Button Data={"Approve"} onClick={handleApprove}/>
      <Button Data={"Reject"} onClick={handleReject}/>
      {isRemarks?<div>
        <input value={remarks} type="text" placeholder="Remarks" onChange={(e) => setRemarks(e.target.value)}/>    
    <Button Data={"Submit"} onClick={handleRemarks}/>
      </div>
      :null
      }
      </div>
      :null}
    </div>
  );
}

export default ExpenseView;
