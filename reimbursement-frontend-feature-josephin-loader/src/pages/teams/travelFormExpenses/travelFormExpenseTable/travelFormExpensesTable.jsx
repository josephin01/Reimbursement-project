import React, { useEffect, useState, useContext } from "react";
import { useNavigate } from "react-router-dom";
import Table from "../../../../components/table/table";
import {
  fetchTableView,
  fetchTeamsExpenseTableData,
} from "../../../../action/api/Api_config";
import { teamsExpenseTableHeader } from "../../../../constants/constant";
import { AppContext } from "../../../../context/context";
import { useSelector, useDispatch } from "react-redux";
import Search from "../../../../components/search1/search1";
import './travelFormExpenseTable.scss'

function TravelFormExpensesTable() {
  const user = useSelector((state)=>state.user.user)
  const tableData = useSelector(
    (state) => state.teamTravelExpense.teamTravelExpense
  );
  const dispatch = useDispatch();
  const [selectedStatus, setSelectedStatus] = useState("ALL");
  const { viewData,selectedList } = useContext(AppContext);
  const [viewDataValue, setViewDataValue] = viewData;
  const [selectedListValue, setSelectedListValue] = selectedList;
  const [items,setItems]=useState(tableData)
  const navigate = useNavigate();

  useEffect(() => {
    fetchTeamsExpenseTableData(dispatch,selectedStatus,user);
  }, [selectedStatus]);

  const handleExpenseView=(row)=>{
    setSelectedListValue(row);
    fetchTableView(selectedListValue.id, setViewDataValue)
    .then((response)=>{
      if(response){
        navigate("TravelFormExpenseView")
      }
    })
  }

  const fetchView = () => {

  }

  return (
    <div>
      <div className="searchContainer">
          <Search travelData={tableData} setItems={setItems}/>
      </div>
      <div className="expensesTableContent">
      {Array.isArray(items) && items.length > 0 ? (
        <Table
          headers={teamsExpenseTableHeader}
          Data={items}
          isView={true}
          setShowRow={handleExpenseView}
          setStatus={setSelectedStatus}
          fetchView={fetchView}
        />
      ) : (
        <p>Loading...</p>
      )}
      </div>
    </div>
  );
}

export default TravelFormExpensesTable;
