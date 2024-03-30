import React from 'react'
import { Outlet } from 'react-router-dom'
import "../layout.scss";


function Batch() {
  return (
     <div className='TeamContainer'>
        <div className='LayoutTitle'>Batches</div>
        <div className="outletContainer"><Outlet/></div>
    </div>
  )
}

export default Batch