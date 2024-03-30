import React,{useContext,useState} from 'react'
import { AppContext } from '../../../context/context'
import DisplayForm from '../../../components/forms/displayform/displayform';
import { teamsTravelFormHeaders } from '../../../constants/constant';
import Button from '../../../components/buttons/buttons';
import { teamsTravelFormStatus } from '../../../action/api/Api_config';
import { useLocation } from 'react-router-dom';

function AdminTravelFormView() {
  const { viewData,travelFormStatus } = useContext(AppContext);
  const [viewDataValue, setViewDataValue] = viewData;
  const [status,setStatus]=travelFormStatus;
  const [success, setSuccess] = useState(false);
  const [failure,setFailure] = useState(false);
  const [error,setError]=useState(false)
  const location=useLocation()
  const showRow = location.state.showRow

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
      <DisplayForm 
        headers={teamsTravelFormHeaders}
        Data={viewDataValue}
      />
      <div>
      {status.replace(/_/g, " ") === "FORM PENDING" && (
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
      </div>
    </div>
  )
}

export default AdminTravelFormView