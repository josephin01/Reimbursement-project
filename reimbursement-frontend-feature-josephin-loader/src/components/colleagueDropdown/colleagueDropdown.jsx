import React from "react";
import "./colleagueDropdown.scss";

function ColleagueDropdown({ value }) {
  return (
    <div className="dropdownCard">
      <img className="dropdownImage" src={value.profile} alt="image" />
      <div className="nameEmail">
        <div className="dropdownTitle">{value.firstName}</div>
        <div className="dropdownEmail">{value.email}</div>
      </div>
    </div>
  );
}

export default ColleagueDropdown;
