import axios from "axios";
import { setExpense } from "../../redux/slice/expenseSlice";
import { setTeamExpense } from "../../redux/slice/teamExpenseClaim";
import { headers, BASE_URL, BASE_URL2,dummyData } from "./Api_Constants";
import { setTeamTravelExpense } from '../../redux/slice/teamTravelExpenseSlice';
import { setExpenseClaim } from "../../redux/slice/expenseClaimSlice";
import { setColleague, setExpenseType, setManager, setProject } from "../../redux/slice/expenseClaimDetailSlice";

export const fetchTeamsTableData = async (setTravelData, status,empId) => {
  try {
    const response = await axios.get(`${BASE_URL2}travelForm/manager/${empId}/${status}`,headers);
    const data = response.data.data;
    setTravelData(data);
    return response
  } catch (error) {
    console.error(error);
    return error
  }
};

export const purposeData = async (setPurpose, setLoading) => {
  setLoading(true);
  try {
    const res = await axios.get(
      `${BASE_URL2}admin/purposeofvisit`,
      headers
    );
    setPurpose(res.data.data);
  } catch (error) {
    console.error(error);
  }
  setLoading(false);
};

export const collegueData = async (dispatch) => {
  try {
    const res = await axios.get(`${BASE_URL2}employees/colleagues`, {
      headers: {
        "Content-Type": "application/json",
      },
    });
    const data = res.data.data;
    dispatch(setColleague(data));
  } catch (error) {
    console.error(error);
  }
};

export const projectNameData = async (setProjectName) => {
  try {
    const res = await axios.get(`${BASE_URL2}projects/allProjects`,{},headers);
    const data = res.data.data;
    setProjectName(data);
  } catch (error) {
    console.error(error);
  }
};

export const viewStatusData = async (id, setViewDataValue) => {
  console.log(id,"oooooooooooooooooooo")
  try {
    const res = await axios.get(`${BASE_URL2}travelForm/${id}`, headers);
    setViewDataValue(res.data.data);
    return res

  } catch (error) {
    console.error(error);
    return error
  }
};

export const expenseTypeData = async (setExpenseTpe) => {
  const request = {
    formType: "TRAVEL_FORM",
  }
    try {
      const res = await axios.get(`${BASE_URL2}admin/expensetype`, {
        headers: {
          "Content-Type": "application/json",
        },
      });
      const data = res.data.data;
      setExpenseTpe(data);
    } catch (error) {
      console.error(error);
    }
  };  

export const fetchTableData = async (setTravelData,status,empID) => {
  console.log(empID,status)
      try {
      const response = await axios.get(`${BASE_URL2}travelForm/employee/${empID}/${status}`, headers);
      const data = response.data.data;
      setTravelData(data);
    } catch (error) {
      console.error(error);
    }
  };

  export const deletePurposeDetailsAPI = async (deleteID) => {
    const requestBody = {
      id: deleteID
    };
    try {
      const response = await axios.delete(`${BASE_URL2}admin/purposeofvisit`, {
        data: requestBody,
        headers: headers
      });
    }
      catch (error){
        console.error(error,"error fetching colleagues")
      }
  }

export const projectData = async (setProject) => {
  try {
    const response = await axios.get(`${BASE_URL2}projects/all`);
    setProject(response.data.data);
  } catch (error) {
    console.error(error, "error fetching data");
  }
};



export const fetchView = async (id, setViewDataValue) => {
  try {
    const response = await axios.get(`${BASE_URL2}expense-claims/${id}`);
    const param = response.data.data;
    setViewDataValue(param);
  } catch (error) {
    console.error(error);
  }
};

export const handleViewFormAPi = async (
  setData,
  setExpense,
  navigate,
  passData
) => {
  try {
    const res = await axios.post(
      `${BASE_URL2}batches/filters`,
      passData,
      {
        headers: {
          "Content-Type": "Application/json",
        },
      }
    );
    const formattedData = res.data.data.map(({ expensesList, ...rest }) => {
      return {
        ...rest,
      };
    });

    const formattedExpenseData = res.data.data.map(
      ({ expensesList }) => expensesList
    );

    setData(formattedData);
    setExpense(formattedExpenseData);
    navigate("/Dashboard/Batch/TableView", {
      state: { data: formattedData, expense: formattedExpenseData },
    });
  } catch (error) {
    console.error(error);
  }
};

export const fetchDataApi = async (setBatchData, batchData) => {
  try {
    const res = await axios.get(
      `${BASE_URL2}batches/batches_history`
    );
    if (res.data && Array.isArray(res.data.data)) {
      const processedData = res.data.data.map(
        ({ fromDate, toDate, ...rest }) => rest
      );
      await setBatchData(processedData);
    } else {
      setBatchData([]);
    }
  } catch (error) {
    console.error("Error fetching data:", error);
  }
};

export const fetchViewApi = async (id, setData, setExpense, navigate) => {
  try {
    const respo = await axios.get(`${BASE_URL2}batches/${id}`);
    const formattedData = respo.data.data.expensesDto.map(
      ({ expensesList, ...rest }) => {
        return { ...rest };
      }
    );

    const Expenses = respo.data.data.expensesDto.map(
      ({ expensesList }) => expensesList
    );

    setData(formattedData);
    setExpense(Expenses);

    navigate("TableView", {
      state: { data: formattedData, expense: Expenses },
    });
  } catch (error) {
    console.error(error);
  }
};

export const teamsTravelFormStatus = async (id, status) => {
  try {
    const response = await axios.put(`${BASE_URL2}travelForm/updateStatus/${id}`,status,{headers});
    return response
  } catch (error) {
    return error
  }
}

export const teamsTravelFormExpensesStatus = async (id, status) => {
  try {
    const response = await axios.put(
      `http://localhost:8000/expenses/approve/travel_form_id/${id}`,
      status,
      { headers }
    );
  } catch (error) {
    console.error(error);
  }
}

export const fetchEmployees = async (setEmployeeData) => {
  try {
    const response = await axios.get(
      `${BASE_URL2}employees/all`,
      {},
      {
        headers: {
          "Content-Type": "Application/json",
        },
      }
    );
    await setEmployeeData(response.data.data);
  } catch (error) {
    console.error("Failed to fetch employees:", error);
  }
};

export const employeeDeleteAPI = async (deleteID) => {
  try {
    const response = await axios.put(
      `${BASE_URL2}employees/delete/${deleteID}`,
      {
        headers: {
          "Content-Type": "application/json",
        },
      }
    );
  } catch (error) {
    console.error(error);
  }
};

export const roleChanging = async (data) => {
  try {
    const response = await axios.put(`${BASE_URL2}roles`, data);
    return response
  } catch (error) {
    console.error(error);
    return response
  }
};

export const expenseTypeDetailsAPI = async (dispatch) => {
  try {
    const response = await axios.get(`${BASE_URL2}admin/expensetype`);
    dispatch(setExpenseType(response.data.data));
  } catch (error) {
    console.error(error);
  }
};

export const expenseFormTypeAPI = async (setData) => {
  try {
    const response = await axios.get(`${BASE_URL2}admin/formtype`);
    setData(response.data);
  } catch (error) {
    console.error(error);
  }
};

export const expenseFormEditAPI = async (data) => {
  try {
    const response = await axios.put(
      `${BASE_URL2}admin/expensetype`,
      data,
      {
        headers: {
          "Content-Type": "application/json",
        },
      }
    );
  } catch (error) {
    console.error(error);
  }
};

export const fetchManagerName = async (dispatch,user) => {
  console.log(user.role,"role")

   const role = (user.role == 1 || user.role == 3) ? "EMPLOYEE" : "MANAGER";
  try {
    const response = await axios.get(`${BASE_URL2}manager/${role}`);
    // const response = await axios.get(`${BASE_URL2}manager`);
    dispatch(setManager(response.data.data))
  } catch (error) {
    console.log(error, "error fetching Manager Data");
  }
};

export const expenseFormDetailsAddAPI = async (data) => {
  try {
    const response = await axios.post(
      `${BASE_URL2}admin/expensetype`,
      data,
      {
        headers: {
          "Content-Type": "application/json",
        },
      }
    );
  } catch (error) {
    console.error(error);
  }
};

export const expenseFormDeleteAPI = async (deleteID) => {
  const body = {
    id: deleteID,
  };
  try {
    const response = await axios.delete(
      `${BASE_URL2}admin/expensetype`,
      {
        data: body,
        headers: {
          "Content-Type": "application/json",
        },
      }
    );
  } catch (error) {
    console.error(error);
  }
};

export const saveAndSend = async (travelId, expensesList) => {

  try {
    const requestBody = {
      id: travelId,
      expensesList: expensesList.map(
        ({ id, title, colleague, ...rest }) => rest
      ),
    };
    const res = await axios.post(
      "http://localhost:8000/api/v1/expenses",
      requestBody,
      headers
    );
    return res
  } catch (error) {
    return error
  }
};

export const fetchData = async (setCardCount, teamView, id) => {
  const requestBody = {
    isTeam: teamView,
  };
  
  try {
    const response = await axios.post(`http://localhost:8000/api/v1/expenses/expenses_count/${id}`,
      requestBody,
      headers
    );
    const Data = response.data.data;
    setCardCount(Data);
  } catch (error) {
    console.error(error);
  }
};


export const fetchProgressData = async (setProgressData) => {
  try {
    const response = await axios.get("http://localhost:8000/api/v1/expenses/amount_spent",headers);
    const Data = response.data.data;
    setProgressData(Data);
  } catch (error) {
    console.error(error);
  }
};
export const fetchExpenseTableData = async (dispatch, isCardSelect,user) => {
  try {
    const requestBody = {
      empId: user.empId,
      roleName: user.role==1?"ADMIN":user.role==2?"MANAGER":"EMPLOYEE",
      isTeam: user.role == 1 ? true : false,
    };
    const response = await axios.post(`http://localhost:8000/api/v1/expenses/status?expenseStatus=${isCardSelect}`,
      requestBody,
      headers
    );
    if (response.data && Array.isArray(response.data.data)) {
      const formattedData = response.data.data.map((value) => {
        return {
          ...value,
          expenseDate: value.expenseDate.split("T")[0],
        };
      });
      dispatch(setExpense(formattedData));
    }
  } catch (error) {
    console.error(error);
  }
};

export const fetchExpenseClaimCount = async (setExpenseCount) => {
  try {
    const response = await axios.get(`${BASE_URL2}expense-claims/count`,headers);
    const Data = response.data.data;
    setExpenseCount(Data);
  } catch (error) {
    console.error(error);
  }
};

export const fetchTravelCount = async (setTravelCount) => {
  try {
    const response = await axios.get(`${BASE_URL2}travelForm/travel_form_count`,headers);
    const Data = response.data.data;
    setTravelCount(Data);
  } catch (error) {
    console.error("Error",error);
  }
};



export const fetchTableView = async (id, setViewDataValue) => {
  try {
    const response = await axios.get(`http://localhost:8000/api/v1/expenses/travel_form_id/${id}`,headers);
    const Data = response.data.data;
    setViewDataValue(Data);
    return(Data)
  } catch (error) {
    console.error(error);
  }
};

export const fetchTeamsExpenseTableData = async (dispatch, isCardSelect,user) => {
  try {
    const requestBody = {
      empId: user.empId,
      roleName:user.role==1?"ADMIN":user.role==2?"MANAGER":"EMPLOYEE" ,
      isTeam: true,
    };
    const response = await axios.post(`http://localhost:8000/api/v1/expenses/status?expenseStatus=${isCardSelect}`,
      requestBody,
      headers
    );
    if (response.data && Array.isArray(response.data.data)) {

      const formattedData = response.data.data.map((value) => {
        let formattedStatus;
        switch (value.expenseStatus) {
            case "MANAGER_REJECTED":
                formattedStatus = "Manager Rejected";
                break;
            case "MANAGER_APPROVED":
                formattedStatus = "Manager Approved";
                break;
            case "ADMIN_APPROVED":
                formattedStatus = "Admin Approved";
                break;
            case "ADMIN_REJECTED":
                formattedStatus = "Admin Rejected";
                break;
            case "PENDING":
                formattedStatus = "Pending";
                break;
            default:
                formattedStatus = value.expenseStatus;
        }
    
        return {
            ...value,
            expenseDate: value.expenseDate.split("T")[0],
            expenseStatus: formattedStatus,
        };
    });
    
    dispatch(setTeamTravelExpense(formattedData));
    }
  } catch (error) {
    console.error(error,"eroooooooooooooooooo");
  }
};

export const fetchStatusChange = async (status, remarks, id) => {
  try {
    const requestBody = {
      expenseStatus: status,
      remarks: remarks.remarks,
    };
    const response = await axios.put(
      `${BASE_URL2}expenses/approve/travel_form_id/${id}`,
      requestBody,
      headers
    );
    return response
  } catch (error) {
    console.error(error);
    return error
  }
};
export const employeeNameReport = async (setEmployeeName) => {
  try {
    const res = await axios.get(
      `${BASE_URL2}employees/getemployeeid&name`,
      {
        headers: {
          "Content-Type": "application/json",
        },
      }
    );
    const data = res.data.data;
    
    setEmployeeName(data);
  } catch (error) {
    console.error(error);
  }
};
export const expenseTypeDataReport = async (setExpenseTypeReport) => {
  try {
    const res = await axios.get(
      `${BASE_URL2}admin/getallexpensetype`,
      {
        headers: { "Content-Type": "application/json" },
      }
    );
    const data = res.data.data;
    setExpenseTypeReport(data);
  } catch (error) {
    console.error(error);
  }
};
export const projectNameReportData = async (setProjectNameReport) => {
  try {
    const res = await axios.get(
      `${BASE_URL2}projects/report`,
      {
        headers: {
          "Content-Type": "application/json",
        },
      }
    );
    const data = res.data.data;
    setProjectNameReport(data);
  } catch (error) {
    console.error(error);
  }
};
export const statusReport = async (setStatusNameReport) => {
  try {
    const res = await axios.get(
      `${BASE_URL2}expenses/getexpensestatus`,
      {
        headers: {
          "Content-Type": "application/json",
        },
      }
    );
    const data = res.data.data;
    setStatusNameReport(data);
  } catch (error) {
    console.error(error);
  }
};

export const employeeNotificationAPI = async (setAdminNotification) => {
  try {
    const response = await axios.get(`${BASE_URL}notification/employee/${user.empId}`);
    const data = response.data.data;
    setAdminNotification(data);
  } catch (error) {
    console.log(error);
  }
};

export const managerNotificationAPI = async (setNotificationData,user) => {

  try {
    const response = await axios.get(`${BASE_URL}notification/manager/${user.empId}`);
    const data = response.data.data;
    setNotificationData(data);
  } catch (error) {
    console.log(error);
  }
};

export const adminNotification = async(setNotification) => {
  try {

    const response = await axios.get(`${BASE_URL}notification/admin`);
    const Data = response.data.data;
    setNotification(Data);

  } catch (error) {
    console.log(error);
  }
}

export const fetchAllProjectsAPI = async (
  postProject,
  setProject,
  closePopUp
) => {
  try {
    const res = await axios.post(`${BASE_URL2}projects`, postProject, headers);
   
    setProject([...project, res.data]);
    closePopUp();
  } catch (error) {
    console.log(error, "error adding project");
  }
};

export const fetchProjectsAPI = async (dispatch) => {
  try {
    const res = await axios.get(
      `${BASE_URL2}projects/all`,
      headers
    );
    dispatch(setProject(res.data.data))
  } catch (error) {
    console.log(error, "error adding project");
  }
};

export const deleteProjectAPI = async (row) => {
  try {
    const response = await axios.delete(`${BASE_URL}projects/delete/${row.id}`);
  } catch (error) {
    console.log(error, "error deleting project details");
  }
};

export const projectEditAPI = async (update, setNewProject, closePopUp) => {
  try {
    const response = await axios.put(
      `${BASE_URL2}projects/${update.id}`,
      update,
      {
        headers: {
          "Content-Type": "application/json",
        },
      }
    );
    setNewProject("");
    closePopUp();
  } catch (error) {
    console.error("Error updating project:", error);
  }
};


export const fetchExpenseClaimData=async(dispatch,status,empID)=>{
  try{
    const response=await axios.get(`${BASE_URL2}expense-claims/history/${empID}/${status}`,headers)
    dispatch(setExpenseClaim(response.data.data))
  }
  catch(error){
    console.error(error)
  }
}


export const fetchExpenseViewAPI = async (id, setViewDataValue) => {
  try {
    const response = await axios.get(
      `${BASE_URL2}expense-claims/${id}`,
      headers
    );
    setViewDataValue(response.data.data)
    return response
  } catch (error) {
    console.log(error);
  }
};

export const teamExpenseStatusChaningAPI = async (managerStatus) => {

  try {
    const response = await axios.put(
      `${BASE_URL2}expense-claims/approval`,
      managerStatus
    );
    return response;
  } catch (error) {
    console.log(error);
    return response
  }
};

export const fetchTeamExpenseClaimData = async (data, setData) => {
  try {
    const response = await axios.get(
      `${BASE_URL2}expense-claims/manager/view/${data.managerId}/${data.status}`
    );
    
    if (response.data && Array.isArray(response.data.data)) {

      const formattedData = response.data.data.map((value) => {
        let formattedStatus;
        switch (value.expenseStatus) {
            case "MANAGER_REJECTED":
                formattedStatus = "Manager Rejected";
                break;
            case "MANAGER_APPROVED":
                formattedStatus = "Manager Approved";
                break;
            case "ADMIN_APPROVED":
                formattedStatus = "Admin Approved";
                break;
            case "ADMIN_REJECTED":
                formattedStatus = "Admin Rejected";
                break;
            case "PENDING":
                formattedStatus = "Pending";
                break;
            default:
                formattedStatus = value.expenseStatus;
        }
    
        return {
            ...value,
            applyDate: value.applyDate.split("T")[0],
            expenseStatus: formattedStatus,
        };
    });
    
      setData(formattedData);
      return formattedData
    }
  } 
   catch (error) {
    console.log(error);
  }
};

export const expenseFormSubmitAPI = async (expense) => {
  try {
    const response = await axios.post(`${BASE_URL2}expense-claims`,expense);

    return response

  } catch (error) {
    console.log(error);
  }
};

export const fetchExpenseStatusAPI = async (status, setExpenseData) => {
  try {
    const response = await axios.get(`${BASE_URL2}expense-claims/admin-view/status/${status.title}`, headers);
    if (response.data && Array.isArray(response.data.data)) {
            const formattedData = response.data.data.map((value) => {
              return {
                ...value,
                applyDate: value.applyDate.split("T")[0],
              };
            });
            setExpenseData(formattedData);
          }
    
  } catch (error) {
    console.log(error);
  }
}

export const fetchTeamViewExpense = async(expense,dispatch) => {

  try {

    const response = await axios.get(`${BASE_URL2}expense-claims/${expense.id}`);
    const Data = response.data.data;
    dispatch(setTeamExpense(Data));
    
  } catch (error) {

    console.log(error);
    
  }
}

export const teamExpenseClaimStatus= async(setTableData,isTeam,id) =>{
  const requestBody={
    isTeam:isTeam
  }
  try{
    const response = await axios.post(`${BASE_URL2}expense-claims/getexpenseclaimscount/${id}`,requestBody,headers)
    const data = response.data.data
    setTableData(data)
  }
  catch(error){
    console.log(error)
  }
}

export const travelFormTable= async(addDataValue) =>{
  try{
    const response = await axios.post(`${BASE_URL2}travelForm/`,addDataValue,headers)
    console.log(response,"ooooooooooo")
    return response
  }
  catch(error){
    return error
  }
}

export const AdminTravelFormRole=async(state)=>{
  try{
    const response = await axios.get(`${BASE_URL2}travelForm/getTravelFormByRole/${state}`,headers)

    return response;
  }
  catch(error){
    console.log(error)
    return error
  }

}

export const AdminTravelFormStatus=async(status)=>{
  try{
    const response = await axios.get(`${BASE_URL2}travelForm/admin/${status}`,headers)
    return response;
  }
  catch(error){
    console.log(error)
    return error
  }

}