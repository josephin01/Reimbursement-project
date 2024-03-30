import React, { useContext, useState } from "react";
import { useLocation } from "react-router-dom";
import DisplayForm from "../../../../components/forms/displayform/displayform";
import Button from "../../../../components/buttons/buttons";
import "./travelFormView.scss";
import {teamsTravelFormHeaders } from "../../../../constants/constant";
import { AppContext } from "../../../../context/context";
import { teamsTravelFormStatus } from "../../../../action/api/Api_config";

function TeamTravelFormView() {
  const location = useLocation();
  const showRow = location.state ? location.state.showRow : {};
  const { viewData } = useContext(AppContext);
  const [viewDataValue, setViewDataValue] = viewData;
  const [success, setSuccess] = useState(false);
  const [failure,setFailure] = useState(false);
  const [error,setError]=useState(false)

  const handleFormStatus=async(id,status)=>{
    try {
          await teamsTravelFormStatus(id, status).then((response)=>{

            if(response.status==200&&status=="FORM_APPROVED"){
              setSuccess(true)
              setTimeout(() => {
                setSuccess(false);
                window.history.back();
              }, 3000);
            }
            else if(response.status==200&&status=="FORM_REJECTED"){
              setFailure(true)
              setTimeout(() => {
                setFailure(false);
                window.history.back();
              }, 3000);
            }
            else{
              setError(true)
              setTimeout(() => {
                setError(false);
              }, 3000);
            }
          });
          
        } catch (error) {
            console.log("Error submitting form",error)
        }
  }

  return (
    <div>
        {success?<div className="successMessage1">
        Form Approved Successfully
      </div>:failure?<div className="successMessage1">
        Form Rejected Succesfully
      </div>:null}

      <DisplayForm Data={viewDataValue} headers={teamsTravelFormHeaders} />
      {showRow && typeof showRow.status === "string" && (
        <div className="buttonForm">
          <div className="buttonTeamTravelForm">
            {showRow.status.replace(/_/g, " ") === "FORM PENDING" && (
              <>
                <div
                  onClick={() => handleFormStatus(showRow.travelId, "FORM_REJECTED")}
                >
                  {" "}
                  <Button Data="Reject" />
                </div>
                <div
                  onClick={() => handleFormStatus(showRow.travelId, "FORM_APPROVED")}
                >
                  <Button Data="Approve"/>
                </div>
              </>
            )}
            {showRow.status.replace(/_/g, " ") === "FORM REJECTED" && (
              <div onClick={() => handleApprove(showRow.travelId, "FORM_APPROVED")}>
                <Button Data="Reapprove" />
              </div>
            )}
          </div>
        </div>
      )}
    </div>
  );
}

export default TeamTravelFormView;
