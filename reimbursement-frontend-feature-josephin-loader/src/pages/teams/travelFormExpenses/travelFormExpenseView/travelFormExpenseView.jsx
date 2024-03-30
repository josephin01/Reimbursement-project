import React from "react";
import StatusFlow from "../../../../components/statusFlow/statusFlow";
import DisplayForm from "../../../../components/forms/displayform/displayform";
import Button from "../../../../components/buttons/buttons";
import {
  travelFormHeaders,
  expenseHeaders,
  teamsTravelFormHeaders,
} from "../../../../constants/constant";
import { useContext } from "react";
import { AppContext } from "../../../../context/context";
import { useState } from "react";
import { Data,ExpenseData } from "../../../../assets/mockData/mockData";
import { fetchStatusChange } from "../../../../action/api/Api_config";
import './travelFormExpenseView.scss'
import {teamsTravelFormExpensesStatus} from '../../../../action/api/Api_config'
import { useSelector } from "react-redux";

function TravelFormExpenseView() {
  const user=useSelector((state)=>state.user.user)
  const { viewData,selectedList } = useContext(AppContext);
  const [viewDataValue, setViewDataValue] = viewData;
  const [selectedListValue, setSelectedListValue] = selectedList;
  const [isRemarks,setIsRemarks]=useState(false)
  const [remarks,setRemarks]=useState("")
  const [status,setStatus]=useState("")
  const expenseList = viewDataValue.expensesList;
  const { expensesList, ...travelFormData } = viewDataValue;
  const [successMessage,setSuccessMessage] = useState("")
  const [success,setSuccess] = useState(false)
  const [failure,setFailure] = useState(false)
  const [error,setError] = useState(false)

  const handleApprove = () =>{
    setStatus("MANAGER_APPROVED")
    fetchStatusChange("MANAGER_APPROVED",remarks,selectedListValue.id)

  }
  const handleReject=()=>{
    setIsRemarks(true)
  }
  const handleRemarks = () =>{
    fetchStatusChange("MANAGER_REJECTED",remarks,selectedListValue.id)
  }

  console.log(status,"status",remarks)

  
  return (
    <div className="form1Container">
    {successMessage && (
      <div className={`successMessage ${successMessage === 'Rejected!' ? 'show rejectedMessage' : successMessage === 'Approved Successfully!' ? 'show' : ''}`}>
        {successMessage}
      </div>
    )}
      <div>
        <StatusFlow
          status={selectedListValue.expenseStatus}
          travelData={Data}
          expenseData={ExpenseData}
        />
      </div>
      <div>
        <div className="ViewTitle">Travel Form</div>
        <div>
          <DisplayForm Data={travelFormData} headers={teamsTravelFormHeaders} />
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
      {user.role==2&&selectedListValue.expenseStatus=="Pending"?
      <div className="ExpenseViewButton">
        <div >
        <Button Data={"Approve"} onClick={handleApprove} />
        </div>
        <div>
        <Button Data={"Reject"} onClick={handleReject}/>
        </div>
      {isRemarks?<div>
      <input value={remarks} type="text" placeholder="Remarks" onChange={(e) => setRemarks(e.target.value)}/>
      <div
       
      >
      <Button Data={"Submit"} onClick={handleRemarks} />
      </div>
      </div>
      :null
      }
      </div>
      :null}
    </div>
  );
}

export default TravelFormExpenseView;
