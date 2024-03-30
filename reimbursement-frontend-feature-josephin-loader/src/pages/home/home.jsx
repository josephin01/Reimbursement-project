import React, { useContext, useEffect, useState } from "react";
import Card from "../../components/cards/statusCards/statusCards";
import DoughnutCharts from "../../components/charts/doughnutChart/doughnutCharts";
import Progress from "../../components/charts/progressChart/progressCharts";
import { Greeting } from "../../components/greeting/greeting";
import Table from "../../components/table/table";
import "./home.scss";
import { useNavigate } from "react-router-dom";
// import { user } from "../../assets/mockData/mockData";
import ExpenseClaimCards from "../../components/cards/expenseClaimCards/expenseClaimCards";
import TravelFormCards from "../../components/cards/travelformcards/travelFormCards";
import {
  fetchData,
  fetchExpenseClaimCount,
  fetchProgressData,
  fetchExpenseTableData,
  fetchTableView,
  fetchTravelCount,
  collegueData,expenseTypeDetailsAPI,fetchManagerName,fetchProjectsAPI
} from "../../action/api/Api_config";
import { AppContext } from "../../context/context";
import { expenseTableHeader } from "../../constants/constant";
import { useSelector, useDispatch } from "react-redux";

function Home() {
  const user = useSelector((state)=>state.user.user)
  const navigate = useNavigate();
  const [isCardSelect, setIsCardSelect] = useState("");
  const [cardCount, setCardCount] = useState({});
  const [progressData, setProgressData] = useState({});
  const dispatch = useDispatch();
  const tableData = useSelector((state) => state.claim.expense);
  const [expenseCount, setExpenseCount] = useState({});
  const [travelCount, setTravelCount] = useState({});
  const { viewData,selectedList } = useContext(AppContext);
  const [viewDataValue, setViewDataValue] = viewData;
  const [selectedListValue, setSelectedListValue] = selectedList;
  const [status,setStatus]=useState("ALL")

  useEffect(() => {
    if(user.role)
    fetchData(setCardCount, user.role===1?true:false,user.empId);
    fetchProgressData(setProgressData);
    fetchExpenseClaimCount(setExpenseCount);
    fetchTravelCount(setTravelCount);
    fetchManagerName(dispatch,user);
    fetchProjectsAPI(dispatch);
    expenseTypeDetailsAPI(dispatch);
    collegueData(dispatch);
  }, []);

  const handleExpenseView=(row)=>{
    setSelectedListValue(row)
    fetchTableView(selectedListValue.id, setViewDataValue)
    .then((response)=>{
      if(response){
        navigate("ExpenseView")
      }
    })
  }

  useEffect(() => {
    if (isCardSelect) {
      fetchExpenseTableData(dispatch, isCardSelect,user);
    }
  }, [isCardSelect]);

  const handleView = (row) => {
    console.log(row);
  }



  return (
    <div className="contentContainer">
      <div className="CardTable">
        <div className="greetingStyle">
          <Greeting />!
        </div>
        <Card
          isCardSelect={isCardSelect}
          setIsCardSelect={setIsCardSelect}
          cardCount={Object.values(cardCount)}
        />
        {!isCardSelect ? (
          user.role == 1 ? (
            <div className="chartsStyle">
              <div className="claimCard">
                <TravelFormCards containerData={Object.values(travelCount)} />
              </div>
              <div className="claimCard">
                <ExpenseClaimCards
                  containerData={Object.values(expenseCount)}
                />
              </div>
            </div>
          ) : (
            <div className="chartsStyle">
              <div className="Doughnut chart">
                <DoughnutCharts cardCount={Object.values(cardCount)} />
              </div>
              <div className="progressChart chart">
                <Progress expenseTypeAmount={Object.values(progressData)} />
              </div>
            </div>
          )
        ) : (
          <div className="tableMain">
            {tableData && (
              <Table
                headers={expenseTableHeader}
                fetchView={handleView}
                Data={tableData}
                isView={true}
                setShowRow={handleExpenseView}
                setStatus={setStatus}
              />
            )}
          </div>
        )}
      </div>
    </div>
  );
}

export default Home;
