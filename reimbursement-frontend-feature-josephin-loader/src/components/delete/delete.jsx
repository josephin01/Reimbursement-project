import React from "react";
import "./delete.scss";
import Button from "../buttons/buttons";
import { deletePurposeDetailsAPI } from "../../action/api/Api_config";
import { deleteData } from "../../constants/constant";
function delete1({ title, setShowPopup,deleteID,deleteAPI,message,setSuccessMessage,reloadData}) {
  const handleDelete = async (deleteID) => {
    try {
      await deleteAPI(deleteID);
      setSuccessMessage(message)
      setShowPopup(false);
      setTimeout(() => {
        reloadData(); 
        setSuccessMessage("");
      }, 2000);
    } catch (error) {
      console.error("Error deleting purpose:", error);
    }
  };

  const handleCancel = () => {
    setShowPopup(false);
  };
  return (
    <div className="popupContainer">
      <div className="popupBox">
        <div className="popupTitle">{deleteData.delete} {title}</div>
        <div className="popupMessage">
          <p>
            This {title} will be completely removed.{"\n"}
            Are you sure you want to delete?
          </p>
        </div>
        <div className="popupButtons">
          <div  onClick={handleCancel}><Button Data="Cancel" /></div>
          <div   onClick={() => handleDelete(deleteID)}><Button Data="Delete" /></div>
        </div>
      </div>
    </div>
  );
}

export default delete1;
