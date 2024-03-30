import React, { useState } from "react";
import Select from "react-select";
import "./report.scss";
import { reportPeriodOptions } from "../../../assets/mockData/mockData";
import { amountOptions } from "../../../assets/mockData/mockData";
import { reportTypeOptions } from "../../../assets/mockData/mockData";
import { reportPage } from "../../../constants/constant";

function ReportForm({
  projectNameReport,
  expenseTypeReport,
  statusNameReport,
  formData,
  setFormData,
  employeeName,
}) {
  const [allSelectedEmployee, setAllSelectedEmployee] = useState(false);
  const [allSelectedExpense, setAllSelectedExpense] = useState(false);

  const expenseTypeList = [
    { label: "All", value: "all" },
    ...expenseTypeReport.map((type) => ({ label: type, value: type })),
  ];

  const employeeNameList = [
    { label: "All", value: "all" },
    ...employeeName.map((employee) => ({
      label: `${employee.firstName} ${employee.lastName}`,
      value: employee.empId,
    })),
  ];
  const [showCustomDates, setShowCustomDates] = useState(false);
  const [showAmount, setShowAmount] = useState(false);
  const handleChangeCustom = (e) => {
    const { name, value } = e.target;
    if (value == "Last 3 months") {
      setShowCustomDates(false);
      const moment = require("moment");
      const currentDate = moment();
      const threeMonthsAgo = moment().subtract(3, "months");
      setFormData({
        ...formData,
        startDate: threeMonthsAgo.format("YYYY-MM-DD"),
        endDate: currentDate.format("YYYY-MM-DD"),
      });
    } else if (value == "Last 6 months") {
      setShowCustomDates(false);
      const moment = require("moment");
      const currentDate = moment();
      const threeMonthsAgo = moment().subtract(6, "months");
      setFormData({
        ...formData,
        startDate: threeMonthsAgo.format("YYYY-MM-DD"),
        endDate: currentDate.format("YYYY-MM-DD"),
      });
    } else if (value == "Last 1 year") {
      setShowCustomDates(false);
      const moment = require("moment");
      const currentDate = moment();
      const threeMonthsAgo = moment().subtract(12, "months");
      setFormData({
        ...formData,
        startDate: threeMonthsAgo.format("YYYY-MM-DD"),
        endDate: currentDate.format("YYYY-MM-DD"),
      });
    } else if (value == "Custom") {
      setShowCustomDates(true);
    }
  };

  const handleClickEmployee = (selectedOptions) => {
    const isAllSelected = selectedOptions?.some(
      (option) => option.value === "all"
    );

    if (isAllSelected) {
      setAllSelectedEmployee(true);
      setFormData({ ...formData, empId: [] });
    } else {
      setAllSelectedEmployee(false);
      const selectedEmpIds = selectedOptions
        ?.filter((option) => option.value !== "all")
        ?.map((option) => option.value);
      setFormData({
        ...formData,
        empId: selectedEmpIds,
      });
    }
  };
  const handleClickExpenseType = (selectedOptions) => {
    const isAllSelected = selectedOptions.some(
      (option) => option.value === "all"
    );

    if (isAllSelected) {
      setAllSelectedExpense(true);
      setFormData({ ...formData, expenses: [] });
    } else {
      setAllSelectedExpense(false);
      const selectedExpenseTypes = selectedOptions
        .filter((option) => option.value !== "all")
        .map((option) => option.value);
      setFormData({
        ...formData,
        expenses: selectedExpenseTypes,
      });
    }
  };
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };
  const handleAmount = (e) => {
    const { name, value } = e.target;
    if (name === "minAmount" || name === "maxAmount") {
      if (formData.minAmount && formData.maxAmount) {
        setShowAmount(showAmount);
      } else {
        setShowAmount(showAmount);
      }
    } else {
      setShowAmount(value === "Select range");
    }
  };
  const handleProjectChange = (event) => {
    const selectedValue = event.target.value;
    if (selectedValue === "All") {
      setFormData({ ...formData, projectName: [] });
    } else {
      setFormData({ ...formData, projectName: [selectedValue] });
    }
  };
  return (
    <form className="reportForm">
      <div className="reportGrid">
        <label>{reportPage.employeeName}</label>
        <Select
          className="select"
          options={employeeNameList}
          onChange={(selectedOptions) => handleClickEmployee(selectedOptions)}
          isMulti={true}
          placeholder="Select Employee Name"
          isOptionDisabled={(option) =>
            allSelectedEmployee && option.value !== "all"
          }
        />
      </div>
      <div className="reportGrid">
        <label>{reportPage.expenseType}</label>
        <Select
          className="select"
          options={expenseTypeList}
          onChange={(selectedOptions) =>
            handleClickExpenseType(selectedOptions)
          }
          isMulti={true}
          placeholder="Select Expense Type"
          isOptionDisabled={(option) =>
            allSelectedExpense && option.value !== "all"
          }
        />
      </div>
      <div className="reportGrid">
        <label>{reportPage.projectName}</label>
        <select
          onChange={handleProjectChange}
          value={formData.projectName}
          name="projectName"
        >
          <option>All</option>
          {projectNameReport.map((option, key) => (
            <option key={key} value={option.projectName}>
              {option.projectName}
            </option>
          ))}
        </select>
      </div>
      <div className="reportGrid">
        <label>{reportPage.status}</label>
        <select
          onChange={handleChange}
          value={formData.status}
          name="expenseStatus"
        >
          <option value="">All</option>
          {statusNameReport.map((status, index) => (
            <option key={index} value={status}>
              {status}
            </option>
          ))}
        </select>
      </div>

      <div className="reportGrid">
        <label>{reportPage.reportPeriod}</label>
        <select
          name="reportPeriod"
          value={formData.reportPeriod}
          onChange={handleChangeCustom}
        >
          {reportPeriodOptions.map((option) => (
            <option key={option.value} value={option.value}>
              {option.value}
            </option>
          ))}
        </select>
      </div>
      <div className="reportGrid">
        <label>{reportPage.amount}</label>
        <select name="amount" onClick={handleAmount} value={formData.amount}>
          {amountOptions.map((option) => (
            <option key={option.value} value={option.value}>
              {option.label}
            </option>
          ))}
        </select>
      </div>
      <div>
        {showCustomDates && (
          <div className="reportGrid">
            <div>
              <label>{reportPage.startDate}:</label>
              <input
                type="date"
                name="startDate"
                value={formData.startDate}
                onChange={handleChange}
              />
            </div>
            <div>
              <label>{reportPage.endDate}:</label>
              <input
                type="date"
                name="endDate"
                value={formData.endDate}
                onChange={handleChange}
              />
            </div>
          </div>
        )}
      </div>
      <div>
        {showAmount && (
          <div className="reportGrid">
            <div>
              <label>{reportPage.minimum}</label>
              <input
                type="text"
                name="minAmount"
                value={formData.minAmount}
                onChange={handleChange}
              />
            </div>
            <div>
              <label>{reportPage.maximum}</label>
              <input
                type="text"
                name="maxAmount"
                value={formData.maxAmount}
                onChange={handleChange}
              />
            </div>
          </div>
        )}
      </div>
      <div className="reportGrid">
        <label>{reportPage.reportType}</label>
        <select
          onChange={handleChange}
          value={formData.reportType}
          name="reportType"
        >
          {reportTypeOptions.map((option) => (
            <option key={option.value} value={option.value}>
              {option.label}
            </option>
          ))}
        </select>
      </div>
    </form>
  );
}

export default ReportForm;
