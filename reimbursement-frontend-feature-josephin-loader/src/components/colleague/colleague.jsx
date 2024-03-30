import React from "react";
import "./colleague.scss";
import { RxCross2 } from "react-icons/rx";

function Colleague({ value, handleDeleteColleague }) {
  return (
    <div className="ColleagueCont">
      <div className="ColleagueName">
        <img className="ColleagueProfile" src={value.image} alt="image" />
        {value.firstName}
      </div>
      <RxCross2 onClick={() => handleDeleteColleague(value)} />
    </div>
  );
}

export default Colleague;
