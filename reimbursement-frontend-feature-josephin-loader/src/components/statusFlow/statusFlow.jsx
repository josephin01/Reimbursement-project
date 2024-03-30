import React from "react";
import GreenArrow from "../../assets/images/greenArrow.png";
import BlueArrow from "../../assets/images/blueArrow.png";
import RedArrow from "../../assets/images/redArrow.png";
import "./statusFlow.scss";
import { FaCheckCircle } from "react-icons/fa";
import { HiMiniClock } from "react-icons/hi2";
import { GiCancel } from "react-icons/gi";
import { statusFlowData } from "../../constants/constant";

function StatusFlow({ expenseData, travelData, status }) {
  const expenseDate = expenseData && expenseData.length > 0 ? expenseData[0].expenseDate : "";
  const times = [1, 2, 3, 4, 5];
  return (
    <div className="travelFlow">
      {times.map((index) => (
        <div className="flowItem" key={index}>
          <div>
            <p className="statusIcon">
              {index == 1 ? (
                <FaCheckCircle />
              ) : index == 2 &&
                status != "FORM REJECTED" &&
                status != "FORM PENDING" ? (
                <FaCheckCircle />
              ) : index == 2 && status == "FORM REJECTED" ? (
                  <GiCancel />
              ) : index == 2 && status == "FORM PENDING" ? (
                <HiMiniClock />
              ) : index == 3 &&
                status != "FORM APPROVED" &&
                status != "FORM REJECTED" &&
                status != "FORM PENDING" ? (
                <FaCheckCircle />
              ) : index == 3 ? (
                <HiMiniClock />
              ) : index == 4 &&
                (status == "MANAGER APPROVED" ||
                  status == "ADMIN APPROVED" ||
                  status == "ADMIN REJECTED") ? (
                <FaCheckCircle />
              ) : index == 4 && status == "MANAGER REJECTED" ? (
<GiCancel />
              ) : index == 4 ? (
                <HiMiniClock />
              ) : index == 5 && status == "ADMIN APPROVED" ? (
                <FaCheckCircle />
              ) : index == 5 && status == "ADMIN REJECTED" ? (
<GiCancel />
              ) : index == 5 ? (
                <HiMiniClock />
              ) : null}
            </p>
            <p className="statusText">
              {index == 1
                ? "Form Submitted"
                : index == 2
                ? "Form Approval"
                : index == 3
                ? "Bill Added"
                : index == 4
                ? "Manager Approval"
                : "Admin Approval"}
            </p>
            <img
              className="Arrow"
              src={
                index == 1
                  ? GreenArrow
                  : index == 2 &&
                    status != "FORM REJECTED" &&
                    status != "FORM PENDING"
                  ? GreenArrow
                  : index == 2 && status == "FORM REJECTED"
                  ? RedArrow
                  : index == 2 && status == "FORM PENDING"
                  ? BlueArrow
                  : index == 3 &&
                    status != "FORM APPROVED" &&
                    status != "FORM REJECTED" &&
                    status != "FORM PENDING"
                  ? GreenArrow
                  : index == 3
                  ? BlueArrow
                  : index == 4 &&
                    (status == "MANAGER APPROVED" ||
                      status == "ADMIN APPROVED" ||
                      status == "ADMIN REJECTED")
                  ? GreenArrow
                  : index == 4 && status == "MANAGER REJECTED"
                  ? RedArrow
                  : index == 4
                  ? BlueArrow
                  : index == 5 && status == "ADMIN APPROVED"
                  ? GreenArrow
                  : index == 5 && status == "ADMIN REJECTED"
                  ? RedArrow
                  : index == 5
                  ? BlueArrow
                  : null
              }
              alt="Image"
            />
          </div>
          {index == 1 ? (
            <p>{statusFlowData.formSubmittedOn} {travelData.applyDate}</p>
          ) : index == 2 &&
            status != "FORM REJECTED" &&
            status != "FORM PENDING" ? (
            <p>{statusFlowData.formApprovedBy} {travelData.managerName}</p>
          ) : index == 2 && status == "FORM REJECTED" ? (
            <p>{statusFlowData.formRejectedBy} {travelData.managerName}</p>
          ) : index == 2 && status == "FORM PENDING" ? null : index == 3 &&
            status != "FORM APPROVED" &&
            status != "FORM REJECTED" &&
            status != "FORM PENDING" ? (
            <p>{statusFlowData.billAddedOn} {expenseDate}</p>
          ) : index == 3 ? null : index == 4 &&
            (status == "MANAGER APPROVED" ||
              status == "ADMIN APPROVED" ||
              status == "ADMIN REJECTED") ? (
            <p>{statusFlowData.billApprovedBy} {travelData.managerName}</p>
          ) : index == 4 && status == "MANAGER REJECTED" ? (
            <p>{statusFlowData.billRejectedBy} {travelData.managerName}</p>
          ) : index == 4 ? null : index == 5 && status == "ADMIN APPROVED" ? (
            <p>{statusFlowData.formApprovedByAdmin}</p>
          ) : index == 5 && status == "ADMIN REJECTED" ? (
            <p>{statusFlowData.formRejectedByAdmin}</p>
          ) : index == 5 ? null : null}
        </div>
      ))}
    </div>
  );
}

export default StatusFlow;
