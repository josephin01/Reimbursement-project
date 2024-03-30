import React,{useContext, useState} from 'react'
import { AppContext } from '../../../context/context';
import { displayFormData,expenseClaimStatusCardsHeaders } from '../../../constants/constant';
import DisplayForm from '../../../components/forms/displayform/displayform';
import Button from '../../../components/buttons/buttons';
import { user } from '../../../assets/mockData/mockData';
import { teamExpenseStatusChaningAPI } from '../../../action/api/Api_config';
import './adminExpenseClaimView.scss'


function AdminExpenseClaimView({Data}) {

    const [success,setSuccess]=useState(false)
    const [failure,setFailure]=useState(false)

    const adminApproved = (data) => {
      const AdminStatus = {
        "expenseClaimId":data.id,
        "status":'ADMIN_APPROVED',
      }
      teamExpenseStatusChaningAPI(AdminStatus).then((response) => {
        if(response.status == 200){
            setSuccess(true)
            setTimeout(()=>{
                setSuccess(false)
                window.history.back()
            },3000);

        }
      })
    }

    const adminReject = (data) => {
      const AdminStatus = {
        "expenseClaimId":data.id,
        "status":'ADMIN_REJECTED',
      }
      teamExpenseStatusChaningAPI(AdminStatus).then((response) => {
        if(response.status == 200){
            setFailure(true)
            setTimeout(()=>{
                setFailure(false)
                window.history.back()
            },3000);

        }
        
      })

    }
 


  return (
    <div>
        {success && <div className='successMessage1'>Exclaim Claim Approved Successfully!</div>}
        {failure && <div className='successMessage1'>Exclaim Claim Rejected!</div>}

       <DisplayForm Data={Data} headers={expenseClaimStatusCardsHeaders} />
       {user.role === "Admin" &&   <div>
     <div style={{display:"flex",justifyContent:'flex-end',gap:'2rem'}}>
        {(Data.expenseStatus === "PENDING" || Data.expenseStatus === "MANAGER_APPROVED") &&(
            <>
            <div onClick={() => adminApproved(Data)} >
            <Button Data = "Approve" />
          </div>
          <div onClick={() => adminReject(Data)}>
            <Button Data = "Reject" />
          </div>
          </>
        )}
        
    </div> 
       </div>}
    </div>
  )
}

export default AdminExpenseClaimView