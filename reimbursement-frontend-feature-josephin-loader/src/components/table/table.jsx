import React, { useState, useEffect } from "react";
import "./table.scss";
import { IoEye } from "react-icons/io5";
import { FaRegEdit } from "react-icons/fa";
import { MdDelete } from "react-icons/md";
import { tableData } from "../../constants/constant";

function Table({ fetchView,Data, isView,setShowRow,headers,setStatus,onEdit,onDelete}) {
  const [statusOptions, setStatusOptions] = useState(["ALL"]);
  const[deleteID,setDeleteID] = useState();
  const [purposeDelete,setPurposeDelete] = useState(false);

  useEffect(() => {
    const uniqueStatus = [...new Set(Data.map((row) => row.expenseStatus||row.status))];
    setStatusOptions(["ALL", ...uniqueStatus]);
  }, [Data]);

  
  const handleForm = (row) => {
    fetchView(row)
    setShowRow(row)
  };
  const handleStatus = (e) => {
    setStatus(e.target.value);
  };

  const handleEdit = (row) => {
    onEdit(row);
  };

  const handleDelete = (row) => {
    setDeleteID(row.id);
    onDelete(row);
  };


  return (
    <div className="TableContainer">
      <div className="TableContent">
      <table>
          <thead>
            <tr>
              {headers.map(header => (
                  <th key={header.key}>{header.label=='Status'?
                  <select onChange={handleStatus} className="tableSelect">
                  {statusOptions.map((value,key)=>(<option key={key} value={value}>{value}</option>))}</select>:
                  header.label}</th>
                ))}
              <th>{tableData.action}</th>
            </tr>
          </thead>
          <tbody>
            {Data.map((item, index) => (
              <tr key={index}>
                {headers.map(header => (
                  <td key={header.key}>
                {header.key === "employee" && (
                  <div className="employee-info">
                    <img src={item.photo} alt="Employee" className="tableEmployeePhoto" />
                    <span>{item[header.key]}</span>
                  </div>
                )}
                {header.key !== "employee" && item[header.key]}
              </td>
                ))}
                {isView ? (
                  <td>
                    <IoEye className="table-icons" onClick={() => handleForm(item)} />
                  </td>
                ) : (
                  <td>
                    <FaRegEdit className="table-icons" onClick={() => handleEdit(item)} />
                    {/* <MdDelete onClick={() => handleDelete(item)} /> */}
                    <MdDelete  className="table-icons"  onClick={() => handleDelete(item)} />

                  </td>
                )}
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}

export default Table;