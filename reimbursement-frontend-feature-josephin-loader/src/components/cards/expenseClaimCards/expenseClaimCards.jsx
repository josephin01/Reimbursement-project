import React, { useState } from "react";
import "./expenseClaimCards.scss";
import { IoIosArrowForward } from "react-icons/io";
import { expenseClaimCards } from "../../../constants/constant";
import { useNavigate } from "react-router-dom";


const ExpenseClaimCards = ({containerData}) => {


const [showTable,setShowTable] = useState(false);
const navigate = useNavigate();

const handleClick = (value) => {
 navigate('AdminExpenseClaimTable',{state:value});

}

return (
    <div className="expense-wrapper">
    <div className="cardTitle">{expenseClaimCards.Expense_Claim}</div>
      {containerData.map((value, key) => (
        <div className="expense-container"  onClick={() => handleClick(value)} >
          <div className="exp-header">
            <div className="exp-description">
              <header className="exp-title">{value.title.replace(/_/g," ")}</header>
              {value.count}
            </div>
            <div className="exp-arrowicon">
              <IoIosArrowForward className="arrow" />
            </div>
          </div>
        </div>
      ))}
    </div>
  );
};

export default ExpenseClaimCards;
 