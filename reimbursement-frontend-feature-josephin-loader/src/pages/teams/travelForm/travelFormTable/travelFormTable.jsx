import React, { useEffect, useState, useContext } from "react";
import Table from "../../../../components/table/table";
import { useNavigate, useSearchParams } from "react-router-dom";
import "./travelFormTable.scss";
import {
  teamsTravelData,
  travelFormTableData,
} from "../../../../constants/constant";
import { fetchTeamsTableData } from "../../../../action/api/Api_config";
import { AppContext } from "../../../../context/context";
import { viewStatusData } from "../../../../action/api/Api_config";
import {
  travelFormTable,
  travelFormStatus,
} from "../../../../constants/constant";
import Search from "../../../../components/search1/search1";
import { useSelector } from "react-redux";

function TravelForm() {
  const [showRow, setShowRow] = useState("");
  const [travelData, setTravelData] = useState({});
  const navigate = useNavigate();
  const [status, setStatus] = useState("ALL");
  const { addData, viewData } = useContext(AppContext);
  const [addDataValue, setAddDataValue] = addData;
  const [viewDataValue, setViewDataValue] = viewData;
  const [items,setItems]=useState({})
  const empID = useSelector((state) => state.user.user.empId);

  const handleRowClick = (status) => {
    setShowRow(status);
  };
  useEffect(() => {
    if (showRow) {
      viewStatusData(showRow.travelId, setViewDataValue);
      navigate("TravelFormView", { state: { showRow } });
    }
  }, [showRow && showRow.travelId]);

  useEffect(() => {
    fetchTeamsTableData(setTravelData, status,empID).then(
      (response)=>{
        if(response.status==200){
          setItems(response.data.data)
        }
      }
    )
  }, [status]);

  const fetchView = () => {
    
  }

  return (
    <div className="teamTravelFormContainer">
      {travelData && Object.keys(travelData).length > 0 && (
        <div className="teamTravelFormContent">
          <div className="teamTravelFormHeading">
            <h3>{travelFormTableData.travelForm}</h3>
          </div>
          <div className="searchContainer">
          <Search travelData={travelData} setItems={setItems}/>
          </div>
          <div className="teamTableContent">
            <Table
              Data={items}
              isView={true}
              setShowRow={handleRowClick}
              headers={teamsTravelData}
              setStatus={setStatus}
              fetchView={fetchView}
            />
          </div>
        </div>
      )}
    </div>
  );
}

export default TravelForm;
