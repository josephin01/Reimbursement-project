import React, { useContext, useEffect, useState } from "react";
import Table from "../../../../components/table/table";
import { teamExpenseClaimTable } from "../../../../assets/mockData/mockData";
import { teamExpenseClaim } from "../../../../constants/constant";
import { useNavigate } from "react-router-dom";
import { AppContext } from "../../../../context/context";
import Card from "../../../../components/cards/statusCards/statusCards";
import { fetchData } from "../../../../action/api/Api_config";
import DisplayForm from "../../../../components/forms/displayform/displayform";
import { teamExpenseFormData } from "../../../../constants/constant";
import { useDispatch, useSelector } from "react-redux";
import { setTeamExpense } from "../../../../redux/slice/teamExpenseClaim";
import { fetchTeamExpenseClaimData,fetchTeamViewExpense,teamExpenseClaimStatus } from "../../../../action/api/Api_config";
import { user } from "../../../../assets/mockData/mockData";
import Search from "../../../../components/search1/search1";
import './expenseClaimTable.scss'

function ExpenseClaimTable() {
  const [showForm, setShowForm] = useState(false);
  const { viewData } = useContext(AppContext);
  const navigate = useNavigate();
  const [formData,setFormData] = useState([]);
  const [teamData,setTeamData] = useState();
  const [status,setStatus] = useState("ALL");
  const [teamViewExpense,setTeamViewExpense] = useState();
  const [items,setItems]=useState({})

  const dispatch = useDispatch();

  const empID = useSelector((state) => state.user.user.empId);

  useEffect(() => {
    const managerData = {
      managerId:empID,
      status:status,
    }
    fetchTeamExpenseClaimData(managerData,setTeamData).then((data)=>{
    setItems(data)
    })
  },[status || managerData])

  const fetchView = (row) => {
    dispatch(setTeamExpense(row))
     setFormData(row);
     navigate('ExpenseClaimView');
     
  }



  return (
    <div>
    <div className="searchContainer">
          <Search travelData={teamData} setItems={setItems}/>
    </div>
    {!showForm && 
    <div className="expenseClaimContent">
      {Array.isArray(items) && items.length > 0 ? (
        items && <Table
          Data={items}
          headers={teamExpenseClaim}
          isView={true}
          setShowRow={setShowForm}
          fetchView={fetchView}
          setStatus={setStatus}
        />
      ) : (
        <div>Loading...</div>
      )}
      </div>}
    </div>
  );
}

export default ExpenseClaimTable;



      
