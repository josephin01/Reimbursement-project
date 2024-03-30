import React, { useEffect, useState } from "react";
import Table from "../../../components/table/table";
import Delete1 from "../../../components/delete/delete"
import "./project.scss";
import { projectData, fetchManagerName, fetchAllProjectsAPI, deleteProjectAPI, projectEditAPI } from "../../../action/api/Api_config";
import { projectTable } from "../../../constants/constant";
import Search from "../../../components/search/search";
import Button from "../../../components/buttons/buttons";
import { useSelector } from "react-redux";

function Project() {
  const [showTable, setShowTable] = useState(true);
  const [showAddForm, setShowAddForm] = useState(false);
  const [showEditForm, setShowEditForm] = useState(false);
  const [editFormData, setEditFormData] = useState(null);
  const [projects, setProjects] = useState([]);
  const [newProject, setNewProject] = useState();
  const [project, setProject] = useState("");
  const [update, setUpdate] = useState({});
  const [managerName,setManagerName] = useState();
  const [postProject,setPostProject] = useState();
  const [popUp,setPopUp] = useState(false);
  const [deleteID,setDeleteID] = useState();
  const [successMessage,setSuccessMessage]=useState("");
  const manager=useSelector((state)=>state.expenseClaimDetail.manager)
  const projectDetails = useSelector((state)=>state.expenseClaimDetail.project)

  const openPopUp = () => {
    setShowAddForm(true);
  };

  const closePopUp = () => {
    setShowAddForm(false);
    setShowEditForm(false);
  };

  const handleEditProject = (row) => {
    setUpdate({...update,id:row.id,managerId:row.managerId});
    setEditFormData(row);
    setShowEditForm(true);
  };

  
  const handleBackButton = () => {
    setShowTable(true);
    setShowAddForm(false);
    setShowEditForm(false);
  };

  const handlePopupSubmit = (editedProject) => {
    const updatedProject = projects.map((project) => {
      if (project.id === editedProject.id) {
        return editedProject;
      }
      return project;
    });
    setProjects(updatedProject);
    setShowEditForm(false);
  };


  useEffect(() => {
    projectData(setProject);
  }, []);

  const reloadProjectData = ()=>{
    projectData(setProject);
  }

  const handleSubmit = async (event) => {
    event.preventDefault();
    fetchAllProjectsAPI(postProject,setProject,closePopUp);
    setSuccessMessage("Project Added Successfully!")
    setTimeout(()=>{
      setSuccessMessage("")
      reloadProjectData()
    },3000)
  };
  const handleProjectDetails = (e) => {
    const { name, value } = e.target;
    setPostProject((prev) => ({ ...prev, [name]: value }));
  };
 
  const handleSave = async (event) => {
    event.preventDefault();
    projectEditAPI(update,setNewProject,closePopUp);
    setSuccessMessage("Project Updated Successfully")
    setTimeout(()=>{
      reloadProjectData()
      setSuccessMessage("")
    },3000)
  };

  const handleDelteClick = (row) => {
    setPopUp(true);
    setDeleteID(row);
  }


  return (
    <div className="projectContainer">
          {successMessage && (
        <div
          className={`successMessage ${successMessage ? 'show' : ''}`}
        >
          {successMessage}
        </div>
          )}
      {showAddForm && (
        <div className="popup">
          <div className="popup-content">
            <h2>Add Project</h2>
            <form>
              <label>Project Name</label>
              <input type="text" name = "projectName" onChange={handleProjectDetails} />
              <br />
              <br />
              <label>Manager</label>
              <select onChange={handleProjectDetails} name="managers">
                <option value="">Select manager</option>
                {manager.map((manager, index) => (
                  <option key={index} value={manager.managerId}>
                    {manager.managerName}
                  </option>
                ))}
              </select>
                
              <br />
              <br />
              <div className="buttons">
                <button
                  type="button"
                  onClick={handleSubmit}
                  className="submitButton"
                >
                  Submit
                </button>
                <button
                  type="button"
                  onClick={closePopUp}
                  className="cancelButton"
                >
                  Cancel
                </button>
              </div>
            </form>
          </div>
        </div>
      )}

      {showEditForm && editFormData && (
        
        <div className="popup">
          <div className="popup-content">
            <h2>Edit project</h2>
            <form onSubmit={() => handlePopupSubmit(editFormData)}>
              <br />
              <label>Project Name</label>
              <input
                type="text"
                id="editProject"
                defaultValue={editFormData.projectName}
                onChange={(e) =>
                  setUpdate((prev) => ({
                    ...prev,
                    projectName: e.target.value,
                  }))
                }
              />
              <br />
              <br />
              <label>Manager</label>
              <input
                type="text"
                id="editProject"
                value={editFormData.managerId}
              />
              <br />
              <br />
              <div className="buttons">
                <button
                  type="button"
                  onClick={() => setShowEditForm(false)}
                  className="cancelButton"
                >
                  Cancel
                </button>
                <button
                  type="submit"
                  onClick={handleSave}
                  className="submitButton"
                >
                  Save
                </button>
              </div>
            </form>
          </div>
        </div>
      )}

      {popUp &&  (
      <Delete1 
        title='Project'  
        deleteAPI={deleteProjectAPI} 
        deleteID={deleteID} 
        setShowPopup = {setPopUp}
        message = {"Project Deleted Successfully!"}
        setSuccessMessage={setSuccessMessage}
        reloadData={reloadProjectData}
         />
      )}

<div className="purposeHeading" onClick={openPopUp}>
          <h1>Project</h1>

           <Button Data = 'Add Project'/>
          </div>

      {showTable && project && project.length > 0 && (
        <div className="project--container">

          
          <div className="tableContent">
            <Search
              headers={projectTable}
              Data={projectDetails}
              isView={false}
              onEdit={handleEditProject}
              onDelete = {handleDelteClick}
              searchBase='projectName'
            />
          </div>
        </div>
      )}
    </div>
  );
}

export default Project;
