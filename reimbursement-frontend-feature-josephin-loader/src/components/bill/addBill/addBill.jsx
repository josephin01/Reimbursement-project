import React from "react";
import "./addBill.scss";
import { RxCross2 } from "react-icons/rx";

function AddBills({ bill, deleteBill }) {
  return (
    <div className="billContainer">
      <div className="billName">{bill.billName}</div>
      <RxCross2 onClick={() => deleteBill(bill.billsUrl)} />
    </div>
  );
}


export default AddBills;
