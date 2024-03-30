import React, { useState } from "react";
import { useLocation } from "react-router-dom";
import { batchForm, expensebillForm } from "../../constants/constant";
import DisplayForm from "../../components/forms/displayform/displayform";
import "./batchform.scss";

function BatchClubView() {
  const location = useLocation();
  const [count, setCount] = useState(0);
  const data = location.state ? location.state.data : [];

  const expenseData = location.state ? location.state.expense : [];
  return (
    <div className="table">
      <div className="sideContent">
        {data.map((element, index) => (
          <div
            key={index}
            onClick={() => setCount(index)}
            className={count == index ? "ActiveContent" : "TextContent"}
          >
            <h4>Travel Form {index + 1}</h4>
          </div>
        ))}
      </div>
      {Object.keys(expenseData).length > 0 && (
  <div>
    <div>
      <DisplayForm Data={data[count]} headers={batchForm} />
      {expenseData[count]?.expenses?.map((expense, eindex) => (
        <div key={eindex}>
          <DisplayForm Data={expense} headers={expensebillForm} />
        </div>
      ))}
    </div>
  </div>
)}
    </div>
  );
}

export default BatchClubView;
