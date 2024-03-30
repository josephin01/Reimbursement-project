import React, { useState,useEffect,useContext } from 'react'
import { useLocation } from 'react-router-dom';
import { AdminTravelFormStatus,AdminTravelFormRole,viewStatusData } from '../../../action/api/Api_config';
import './adminTravelFormTable.scss'
import Table from '../../../components/table/table';
import { adminTravelFormRole,adminTravelFormStatus } from '../../../constants/constant';
import { AppContext } from "../../../context/context";
import { useNavigate} from "react-router-dom";

function AdminTravelFormTable() {
  const { travelFormStatus } = useContext(AppContext);
  const [status, setStatus] = travelFormStatus;
  const [current,setCurrent]=useState("EMPLOYEE")
  const navigate = useNavigate();
  const [showRow, setShowRow] = useState("");   
  const { viewData } = useContext(AppContext);
  const [viewDataValue, setViewDataValue] = viewData;
  const [tableData,setTableData]=useState([])
  useEffect(()=>{
    if(status == "FORM_PENDING"){
        AdminTravelFormRole(current).then((response)=>{
            if(response.status == 200){
                setTableData(response.data.data)
            }
        })
    }
    else{
        AdminTravelFormStatus(status).then((response)=>{
            if(response.status == 200){
                setTableData(response.data.data)
            }
        })
    }
  },[current])

  useEffect(() => {
    console.log("jkdankjk",showRow.travelId)
    if(showRow){viewStatusData(showRow.travelId, setViewDataValue).then((response)=>{
      console.log(response)
      navigate("AdminTravelFormView",{state:{showRow}})
    })
}}, [showRow]);
  console.log(showRow.id,"showRow")


  const handleToggle=(role)=>{
    setCurrent(role)
    console.log(role)
  }


  const function1 =()=>{

  }
  return (
    <div className='adminTravelForm'>
        {status==="FORM_PENDING" ?(<> <div className='adminTravelFormToggle'>
            <a className={current=='EMPLOYEE'?"adminTravelFormContent current":"adminTravelFormContent"} 
            onClick={()=>handleToggle("EMPLOYEE")}>Employee</a>
            <a className={current=='MANAGER'?"adminTravelFormContent current":"adminTravelFormContent"} 
            onClick={()=>handleToggle("MANAGER")}>Manager</a>
            </div>
        <div className='adminTravelFormTable'>
        <Table
              Data={tableData}
              isView={true}
              setShowRow={setShowRow}
              headers={adminTravelFormRole}
            //   setStatus={setStatus}
              fetchView={function1}
            />
        </div>
        </>):
        (<div>
        <Table
              Data={tableData}
              isView={true}
              setShowRow={setShowRow}
              headers={adminTravelFormStatus}
            //   setStatus={setStatus}
              fetchView={function1}
            />
        </div>)
        }
        

        
    </div>
  )
}

export default AdminTravelFormTable