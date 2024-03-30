import React, { useState } from 'react'
import { useLocation } from 'react-router-dom'
import DisplayForm from '../../../../components/forms/displayform/displayform';
import { teamExpenseFormData,teamExpenseClaim,teamExpensedisplayFormData } from '../../../../constants/constant';
import { useSelector } from 'react-redux';
import Button from '../../../../components/buttons/buttons';
import { teamExpenseStatusChaningAPI } from '../../../../action/api/Api_config';
import './expenseClaimView.scss';

function ExpenseClaimView() {
  const data = useSelector((state) => state.teamExpenseClaim.data);
  const handleApprove = (data) => {
   
    const managerStatus = {
      "expenseClaimId":data.id,
      "status":'MANAGER_APPROVED',
    }
    teamExpenseStatusChaningAPI(managerStatus);
    window.history.back();   
  }

  const handleReject = (data) => {

    const managerStatus = {
      "expenseClaimId":data.id,
      "status":'MANAGER_REJECTED',
    }
    teamExpenseStatusChaningAPI(managerStatus); 
    window.history.back();   
  }
 
  return (
    <div>
      <div>
      < DisplayForm  Data={data} headers={teamExpensedisplayFormData} />
      </div>
      <div className='teamExpenseButtons'>
        {data.expenseStatus === "Pending" && (
          <>
        <div onClick={() => handleReject(data)}  >
          <Button Data = "Reject"/>
        </div>
        <div  onClick={() => handleApprove(data)} >
          <Button Data ="Approve" />
        </div>
        </>
        )}
        {data.expenseStatus === "Manager Rejected" && (
          <>
          <div  onClick={() => handleApprove(data)} >
          <Button Data ="ReApprove" />
        </div>
          </>
        )}
      </div>
    </div>
  )
}

export default ExpenseClaimView
