import React, { useState } from "react";
import { Route, Routes } from "react-router-dom";
import { user } from '../assets/mockData/mockData';
import Admin from "../layout/admin/admin";
import Employee from "../layout/employee/employee";
import Manager from "../layout/manager/manager";
// import ExpenseClaim from "../pages/expenseClaim/expenseClaim";
import ExpenseClaim from '../layout/expenseClaim/expenseClaim'
import ExpenseView from "../pages/expenseView/expenseView";
import Home from "../pages/home/home";
import Login from "../pages/login/login";
import ExpenseType from "../pages/manageContent/expenseType/expenseType";
import Project from "../pages/manageContent/project/project";
import Purpose from "../pages/manageContent/purpose/purpose";
import People from "../pages/people/people";
import Report from "../pages/report/report";
import TeamTravelFormExpensesTable from "../pages/teams/travelFormExpenses/travelFormExpenseTable/travelFormExpensesTable";
import TeamTravelFormExpenseView from "../pages/teams/travelFormExpenses/travelFormExpenseView/travelFormExpenseView";
import TeamTravelFormExpenses from "../layout/teamTravelFormExpenses/teamTravelFormExpenses";
import TeamExpenseClaim from "../layout/teamExpenseClaim/teamExpenseClaim";
import TeamTravelForm from "../layout/teamTravelForm/teamTravelForm";
import TeamExpenseClaimTable from "../pages/teams/expenseClaim/expenseClaimTable/expenseClaimTable";
import TeamExpenseClaimView from "../pages/teams/expenseClaim/expenseClaimView/expenseClaimView"
import TeamTravelFormTable from "../pages/teams/travelForm/travelFormTable/travelFormTable"
import TeamTravelFormView from "../pages/teams/travelForm/travelFormView/travelFormView"
// import TravelForm from "../pages/travelForm/travelForm";
import TravelForm from "../layout/travelForm/travelForm";
import TravelFormTable from "../pages/travelForm/travelFormTable/travelFormTable"
import TravelFormView from "../pages/travelForm/travelFormView/travelFormView";
import TravelFormAdd from "../pages/travelForm/travelFormAdd/travelFormAdd";
import BatchTable from "../pages/batches/batchTable";
import BatchClubView from "../pages/batches/batchView";
import BatchForm from "../pages/batches/batchform";
import Batch from "../layout/batch/batch";
import ExpenseClaimTable from "../pages/expenseClaim/expenseClaimTable/expenseClaimTable";
import ExpenseClaimView from "../pages/expenseClaim/expenseClaimView/expenseClaimView";
import ExpenseClaimAdd from "../pages/expenseClaim/expenseClaimAdd/expenseClaimAdd";
import { ProtectRouter } from "../action/authAction";
import AdminExpenseClaimTable from "../pages/adminExpenseClaimTable/adminExpenseClaimTable";
import AdminExpenseClaimView from "../pages/adminExpenseClaimTable/adminExpenseClaimView/adminExpenseClaimView";
import AdminTravelForm from "../pages/adminTravelForm/adminTravelForm";
import AdminTravelFormTable from "../pages/adminTravelForm/adminTravelFormTable/adminTravelFormTable";
import AdminTravelFormView from "../pages/adminTravelForm/adminTravelFormView/adminTravelFormView";
import { useSelector } from "react-redux";

function AppRouters() {

  const user = useSelector((state) => state.user.user.role);
  const[userdata,setUser] = useState(user);
  console.log(user,"[-0==")
  return (
    <Routes>
      <Route path="/" element={<Login />} />
      <Route element = {<ProtectRouter/>}>
      <Route
        path="Dashboard"
        element={
          user == 3 ? (
            <Employee />
          ) : user == 2 ? (
            <Manager />
          ) : (
            <Admin />
          )   
        }
      >
        <Route index element={<Home />} />
        <Route path="AdminExpenseClaimTable" element={<AdminExpenseClaimTable/>}/>
          {/* <Route path="AdminExpenseClaimView" element={<AdminExpenseClaimView/>}/> */}
        {/* </Route> */}
        <Route path="AdminTravelForm" element={<AdminTravelForm/>}>
          <Route index element={<AdminTravelFormTable/>}/>
          <Route path="AdminTravelFormView" element={<AdminTravelFormView/>}/>
        </Route>
        <Route path="Batch" element={<Batch />}>
          <Route index element={<BatchTable />} />
          <Route path="TableView" element={<BatchClubView />} />
          <Route path="AddBatches" element={<BatchForm />} />
        </Route>
        <Route path="ExpenseClaim" element={<ExpenseClaim />}>
        <Route index element = { <ExpenseClaimTable/> } />
          <Route path="ExpenseClaimView" element = {<ExpenseClaimView/>} />  
          <Route path="ExpenseClaimAdd"element={<ExpenseClaimAdd/>}/>
        </Route>
        <Route path="Purpose" element={<Purpose />} />
        <Route path="Project" element={<Project />} />
        <Route path="ExpenseType" element={< ExpenseType/>} />
        <Route path="TeamExpenseClaim" element={<TeamExpenseClaim />}>
          <Route index element={<TeamExpenseClaimTable />}/>
          <Route path="ExpenseClaimView" element={<TeamExpenseClaimView/>}/>
        </Route>
        <Route path="TeamTravelForm" element={<TeamTravelForm />} >
          <Route index element={<TeamTravelFormTable />}/>
          <Route path="TravelFormView" element={<TeamTravelFormView/>}/>
        </Route>
        <Route path="TeamTravelFormExpenses" element={<TeamTravelFormExpenses/>}>
          <Route index element={<TeamTravelFormExpensesTable />}/>
          <Route path="TravelFormExpenseView" element={<TeamTravelFormExpenseView/>}/>
        </Route>
        <Route path="People" element={<People />} />
        <Route path="Report" element={<Report />} />
        <Route path="TravelForm" element={<TravelForm />} >
          <Route index element={<TravelFormTable/>}/>
          <Route path="TravelFormView" element={<TravelFormView/>}/>
          <Route path="TravelFormAdd" element={<TravelFormAdd/>}/>
        </Route>
        <Route path="ExpenseView" element={<ExpenseView/>}/>
      </Route>
      </Route>
    </Routes>
  );
}
export default AppRouters;