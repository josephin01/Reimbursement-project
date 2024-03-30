import React from "react";
import { Outlet } from "react-router-dom";
import "../layout.scss";

function TeamTravelFormExpenses() {
  return (
    <div className="TeamContainer">
      <div className="LayoutTitle">Teams</div>
      <div className="outletContainer">
        <Outlet />
      </div>
    </div>
  );
}

export default TeamTravelFormExpenses;
