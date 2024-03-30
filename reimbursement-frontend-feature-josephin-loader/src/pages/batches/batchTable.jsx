import React, { useState, useEffect } from "react";
import Table from "../../components/table/table";
import Button from "../../components/buttons/buttons";
import "./batches.scss";
import { batchtableData } from "../../constants/constant";
import { useNavigate } from "react-router-dom";
import { fetchDataApi, fetchViewApi } from "../../action/api/Api_config";

function BatchTable() {
  const [showView, setShowView] = useState(false);
  const [batchData, setBatchData] = useState([]);
  const [data, setData] = useState([]);
  const [expense, setExpense] = useState([]);
  const navigate = useNavigate();

  const handleAddBatches = () => {
    navigate("AddBatches");
  };

  useEffect(() => {
     getApIcall();
  }, []);


  const getApIcall = async() => {
   await fetchDataApi(setBatchData,batchData);
  }

  

  const fetchView = (row) => {
    fetchViewApi(row.id, setData, setExpense, navigate);
  };

  return (
    <div className="Container">
      <div>
        <div className="Top-Content">
          <div onClick={handleAddBatches} className="buttoncontainer">
            <Button Data="Add Batches" />
          </div>
        </div>
        <div className="main-content">
          <Table
            Data={batchData}
            isView={true}
            setShowRow={setShowView}
            fetchView={fetchView}
            headers={batchtableData}
          />
        </div>
      </div>
    </div>
  );
}

export default BatchTable;
