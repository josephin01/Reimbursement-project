import React, { useState,useEffect,useContext } from 'react'
import { useNavigate } from 'react-router-dom'
import Button from '../../../components/buttons/buttons'
import Table from '../../../components/table/table'
import { fetchExpenseClaimData, fetchExpenseViewAPI } from '../../../action/api/Api_config'
import { expenseClaimData } from '../../../constants/constant'
import { useDispatch, useSelector } from 'react-redux'
import {AppContext} from '../../../context/context'
import '../expenseClaim.scss'

function ExpenseClaimTable() {
  const navigate=useNavigate()
  const dispatch=useDispatch()
  const { viewData } = useContext(AppContext);
  const [viewDataValue, setViewDataValue] = viewData;
  const tableData=useSelector((state)=>state.expenseClaim.expenseClaim)
  const [row,setRow]=useState({})
  const [status,setStatus]=useState("ALL")
  const empID = useSelector((state)=> state.user.user.empId);
  const handleAdd=()=>{
    navigate("ExpenseClaimAdd")
  }
  useEffect(()=>{
    fetchExpenseClaimData(dispatch,status,empID)
  },[status])

  const handleView=(row)=>{
    fetchExpenseViewAPI(row.id,setViewDataValue)
    .then((response)=>{
      if(response.status==200){
      navigate("ExpenseClaimView")
      }
    })
  }

  return (
    <div className='expenseClaimTable'>
      <div className='expenseClaimButton'><Button Data="Add Expense"  onClick={handleAdd}/></div>
      <div>
        {tableData?
        <Table
          Data={tableData}
          isView={true}
          setShowRow={handleView}
          fetchView={setRow}
          headers={expenseClaimData}
          setStatus={setStatus}
        />:"Loading..."}
      </div>
    </div>
  )
}

export default ExpenseClaimTable