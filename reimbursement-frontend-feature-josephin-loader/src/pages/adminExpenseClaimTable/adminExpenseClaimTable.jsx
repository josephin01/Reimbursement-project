import React, { useEffect, useState } from "react";
import Table from "../../components/table/table";
import { useLocation, useNavigate } from "react-router-dom";
import { fetchExpenseStatusAPI } from "../../action/api/Api_config";
import {
  expenseClaim,
  expenseClaimAdminHeaders,
} from "../../constants/constant";
import AdminExpenseClaimView from "./adminExpenseClaimView/adminExpenseClaimView";

const AdminExpenseClaimTable = () => {
  const location = useLocation();
  const state = location.state;
  const [expenseData, setExpenseData] = useState();
  const [data, setData] = useState();
  const [statusData, setStatusData] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    fetchExpenseStatusAPI(state, setExpenseData);
  }, [state]);

  const handleView = (row) => {
    setStatusData(true);
    setData(row);
  };

  return (
    <div>
      {statusData ? (
        <AdminExpenseClaimView Data={data} />
      ) : (
        expenseData && (
          <Table
            Data={expenseData}
            headers={expenseClaimAdminHeaders}
            isView={true}
            fetchView={handleView}
            setShowRow={setData}
          />
        )
      )}
    </div>
  );
};

export default AdminExpenseClaimTable;

