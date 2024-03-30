import React from 'react'
import { Outlet } from 'react-router-dom'
import "../layout.scss";


function ExpenseClaim() {
  return (
     <div className='TeamContainer'>
        <div className='LayoutTitle'>Expense Claim</div>
        <div className="outletContainer"><Outlet/></div>
    </div>
  )
}

export default ExpenseClaim