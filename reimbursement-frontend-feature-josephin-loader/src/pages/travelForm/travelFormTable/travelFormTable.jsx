import React, { useContext, useEffect, useState } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import Button from "../../../components/buttons/buttons";
import Table from "../../../components/table/table";
import { fetchTableData, viewStatusData } from "../../../action/api/Api_config";
import { AppContext } from "../../../context/context";
import "./travelFormTable.scss";
import { travelFormTable } from "../../../constants/constant";
import { useSelector } from "react-redux";

function TravelFormTable() {
  const location = useLocation();
  const [showRow, setShowRow] = useState("");
  const [showAdd, setShowAdd] = useState(false);
  const [travelData, setTravelData] = useState({});
  const navigate = useNavigate();
  const { addData, viewData } = useContext(AppContext);
  const [addDataValue, setAddDataValue] = addData;
  const [status, setStatus] = useState("ALL");
  const [viewDataValue, setViewDataValue] = viewData;
  const empID = useSelector((state) => state.user.user.empId);



  useEffect(() => {
    viewStatusData(showRow.travelId, setViewDataValue);

    if (showRow) {
      navigate("TravelFormView", {
        state: {
          status: showRow.status.replace(/_/g, " "),
          travelId: showRow.travelId,
        },
      });
    }
  }, [showRow]);

  useEffect(() => {
    fetchTableData(setTravelData, status,empID);
  }, [status]);

  const filteredData = Object.values(travelData).filter(
    (row) => !row.expenseExists
  );
  useEffect(() => {
    if (location.state && location.state.status) {
      const { status } = location.state;
      setStatus(status);
      fetchTableData(setTravelData, status,empID);
    }
  }, [location.state]);

  const handleAddTravelForm = () => {
    setShowAdd((prevShowAdd) => !prevShowAdd);
  };
  useEffect(() => {
    if (showAdd) {
      navigate("TravelFormAdd");
    }
  }, [showAdd]);

  const function1 = () =>{

  }

  return (
    <div>
      <div onClick={handleAddTravelForm} className="travelFormHeading">
            <Button Data="Add Travel Form" />
          </div>
      {travelData && Object.keys(travelData).length > 0 && (
        <div>
          
          <div className="tableContent">
            <Table
              Data={filteredData}
              isView={true}
              setShowRow={setShowRow}
              headers={travelFormTable}
              setStatus={setStatus}
              fetchView={function1}
            />
          </div>
        </div>
      )}
    </div>
  );
}

export default TravelFormTable;
