import React, { useEffect, useState } from "react";
import "./purpose.scss";
import { purposeData } from "../../../action/api/Api_config";
import axios from "axios";
import { purposeTable } from "../../../constants/constant";
import { PurposePageData } from "../../../constants/constant";
import Search from "../../../components/search/search";
import { deletePurposeDetailsAPI } from "../../../action/api/Api_config";
import Delete1 from "../../../components/delete/delete";
import Button from "../../../components/buttons/buttons";
import "./purpose.scss";

function PurposePage() {
  const [showTable, setShowTable] = useState(true);
  const [showAddForm, setShowAddForm] = useState(false);
  const [showEditForm, setShowEditForm] = useState(false);
  const [editFormData, setEditFormData] = useState(null);
  const [purposes, setPurposes] = useState([]);
  const [newPurpose, setNewPurpose] = useState("");
  const [purpose, setPurpose] = useState("");
  const [loading, setLoading] = useState(true);
  const [popUp, setPopUp] = useState(false);
  const [deleteID, setDeleteID] = useState();
  const [successMessage, setSuccessMessage] = useState("");

  const openPopUp = () => {
    setShowAddForm(true);
  };

  const closePopUp = () => {
    setShowAddForm(false);
    setShowEditForm(false);
  };

  const handleEditPurpose = (row) => {
    setEditFormData(row);
    setShowEditForm(true);
  };

  const handleBackButton = () => {
    setShowTable(true);
    setShowAddForm(false);
    setShowEditForm(false);
  };

  const handlePopupSubmit = (editedPurpose) => {
    const updatedPurposes = purposes.map((purpose) => {
      if (purpose.id === editedPurpose.id) {
        return editedPurpose;
      }
      return purpose;
    });
    setPurposes(updatedPurposes);
    setShowEditForm(false);
  };

  const handleDeletePurpose = (row) => {
    setPopUp(true);
    setDeleteID(row.id);
  };

  useEffect(() => {
    purposeData(setPurpose, setLoading);
  }, []);

  const handleSubmit = async (event) => {
    event.preventDefault();
    try {
      const response = await axios.post(
        "http://localhost:8000/api/v1/admin/purposeofvisit",
        { purposes: newPurpose },
        {
          headers: {
            "Content-Type": "application/json",
          },
        }
      );
      setSuccessMessage("Purpose Added Successfully!");
      closePopUp();

      setTimeout(() => {
        purposeData(setPurpose, setLoading);
        setNewPurpose("");
        setSuccessMessage("");
      }, 2000);
    } catch (error) {
      console.error("Error adding purpose:", error);
    }
  };
  const handleSave = async (event) => {
    event.preventDefault();
    try {
      const data = {
        id: editFormData.id,
        purposes: editFormData.purposes,
      };
      const response = await axios.put(
        `http://localhost:8000/api/v1/admin/purposeofvisit`,
        JSON.stringify(data),
        {
          headers: {
            "Content-Type": "application/json",
          },
        }
      );
      setSuccessMessage("Purpose Updated Successfully!");
      closePopUp();

      setTimeout(() => {
        purposeData(setPurpose, setLoading);
        setNewPurpose("");
        setSuccessMessage("");
      }, 2000);
    } catch (error) {
      console.error("Error updating purpose:", error);
    }
  };

  const reloadPurposeData = () => {
    purposeData(setPurpose, setLoading); 
  };



  return (
    <div className="purposeContainer">
      {loading && <p>Loading...</p>}
      {popUp && (
        <Delete1
          title="Purpose"
          deleteAPI={deletePurposeDetailsAPI}
          deleteID={deleteID}
          setShowPopup={setPopUp}
          message={"Purpose Deleted Successfully!"}
          setSuccessMessage={setSuccessMessage}
          reloadData={reloadPurposeData}
        />
      )}
      {successMessage && (
        <div
          className={`successMessage ${
            (successMessage === "Purpose Added Successfully!" ? "show" : "") ||
            (successMessage === "Purpose Updated Successfully!" ? "show" : "") ||
            (successMessage === "Purpose Deleted Successfully!"? "show":"")
          }`}
        >
          {successMessage}
        </div>
      )}
      {showAddForm && (
        <div className="popup">
          <div className="popup-content">
            <h2>{PurposePageData.addPurpose}</h2>
            <form>
              <label htmlFor="purpose">{PurposePageData.purpose}:</label>
              <br />
              <input
                type="text"
                id="purpose"
                value={newPurpose}
                onChange={(e) => setNewPurpose(e.target.value)}
              />
              <br />
              <br />
              <div className="buttons">
                <button
                  type="button"
                  onClick={handleSubmit}
                  className="submitButton"
                >
                  {PurposePageData.submit}
                </button>
                <button
                  type="button"
                  onClick={closePopUp}
                  className="cancelButton"
                >
                  {PurposePageData.cancel}
                </button>
              </div>
            </form>
          </div>
        </div>
      )}

      {showEditForm && editFormData && (
        <div className="popup">
          <div className="popup-content">
            <h2>{PurposePageData.editPurpose}</h2>
            <form onSubmit={() => handlePopupSubmit(editFormData)}>
              <label htmlFor="editPurpose">{PurposePageData.purpose}:</label>
              <br />
              <input
                type="text"
                id="editPurpose"
                value={editFormData.purposes}
                onChange={(e) =>
                  setEditFormData({ ...editFormData, purposes: e.target.value })
                }
              />
              <br />
              <br />
              <div className="buttons">
                <button
                  type="button"
                  onClick={() => setShowEditForm(false)}
                  className="cancelButton"
                >
                  {PurposePageData.cancel}
                </button>
                <button
                  type="submit"
                  onClick={handleSave}
                  className="submitButton"
                >
                  {PurposePageData.save}
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
       <div className="purposeHeading">
            <h3>{PurposePageData.purpose}</h3>
            <div onClick={openPopUp}>
              <Button Data="Add Purpose" />
            </div>
          </div>

      {showTable && purpose && purpose.length > 0 && (
        <div>
         
          <div className="tableContent">
            <Search
              headers={purposeTable}
              Data={purpose}
              isView={false}
              onEdit={handleEditPurpose}
              onDelete={handleDeletePurpose}
              searchBase="purposes"
            />
          </div>
        </div>
      )}
    </div>
  );
}

export default PurposePage;
