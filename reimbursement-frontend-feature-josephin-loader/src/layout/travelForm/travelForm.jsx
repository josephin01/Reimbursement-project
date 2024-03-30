import React from 'react'
import { Outlet } from 'react-router-dom'
import "../layout.scss";
import { layoutData } from '../../constants/constant';

function travelForm() {
  return (
    <div>
         <div className='LayoutTitle'>{layoutData.travelForm}</div>
         <div className="outletContainer"><Outlet/></div>
    </div>
  )
}

export default travelForm