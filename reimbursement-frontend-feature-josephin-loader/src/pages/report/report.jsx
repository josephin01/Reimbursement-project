import React, { useEffect, useState } from "react";
import "./report.scss";
import ReportForm from "../../components/forms/report/report";
import Button from "../../components/buttons/buttons";
import axios from "axios";
import { reportPage } from "../../constants/constant";
import {
  expenseTypeDataReport,
  projectNameReportData,
  statusReport,
  employeeNameReport,
} from "../../action/api/Api_config";

function Report() {
  const [expenseTypeReport, setExpenseTypeReport] = useState([]);
  const [projectNameReport, setProjectNameReport] = useState([]);
  const [statusNameReport, setStatusNameReport] = useState([]);
  const [employeeName, setEmployeeName] = useState([]);
  const [formData, setFormData] = useState({
    empId: [],
    expenses: [],
    projectName: [],
    expenseStatus: null,
    startDate: null,
    endDate: null,
    minAmount: null,
    maxAmount: null,
    reportType: "",
  });

  useEffect(() => {
    expenseTypeDataReport(setExpenseTypeReport);
  }, []);

  useEffect(() => {
    projectNameReportData(setProjectNameReport);
  }, []);

  useEffect(() => {
    statusReport(setStatusNameReport);
  }, []);

  useEffect(() => {
    employeeNameReport(setEmployeeName);
  }, []);

  const [loading, setLoading] = useState(false);
  const handleSubmit = async () => {
    setLoading(true);
    try {
      const response = await axios.post(
        "http://localhost:8000/api/v1/admin/report",
        formData,
        {
          responseType: "blob",
        }
      );
      const url = window.URL.createObjectURL(new Blob([response.data]));
      const link = document.createElement("a");
      link.href = url;
      if (formData.reportType === "excel") {
        link.setAttribute("download", "Report.xlsx");
      } else {
        link.setAttribute("download", "Report.pdf");
      }
      document.body.appendChild(link);
      link.click();
      link.parentNode.removeChild(link);
    } catch (error) {
      console.error("Error downloading file:", error);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="reportContainer">
      <h2>{reportPage.report}</h2>
      <div>
        <ReportForm
          employeeName={employeeName}
          projectNameReport={projectNameReport}
          expenseTypeReport={expenseTypeReport}
          statusNameReport={statusNameReport}
          formData={formData}
          setFormData={setFormData}
        />
      </div>
      <div className="clubButton" onClick={handleSubmit} disabled={loading}>
        <Button Data="club" />
      </div>
      {loading && <div>Loading...</div>}
    </div>
  );
}

export default Report;
