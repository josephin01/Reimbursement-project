import ExpenseClaimForm from "../../../components/forms/expenseClaim/expenseClaimForm";
import { useState } from "react";
import { useEffect  } from "react";
import { collegueData,expenseTypeDetailsAPI,fetchManagerName,fetchProjectsAPI,expenseFormSubmitAPI } from "../../../action/api/Api_config";
import Button from "../../../components/buttons/buttons";
import './expenseClaimAdd.scss'


function ExpenseClaimAdd() {
  const [expenseType,setExpenseType] = useState();
  const [expense,setExpense]=useState([])
  const [project,setProject] = useState();
  const [success,setSuccess]=useState(false)
  const [failure,setFailure] = useState(false)

  useEffect(() => {;
    fetchProjectsAPI(setProject);
    expenseTypeDetailsAPI(setExpenseType);
  },[]);

  const handleAddExpense=(expense)=>{
    console.log(expense)
  }
  const handleSubmit = () =>{
    expenseFormSubmitAPI(expense)
    .then((response)=>{
      if(response.status == 200){
        setSuccess(true)
        setTimeout(()=>{
          setSuccess(false)
          window.history.back()
        },3000)
      }
      else{
        setFailure(true)
        setTimeout(()=>{
          setFailure(false)
          window.history.back()
        },3000)
      }
    })
  
  } 

return(
  <div>
      {success?<div className="successMessage1">
        Expense Claim Added Successfully!
      </div>:failure?<div className="errorMessage">
        Some error occured. Try again
      </div>:null}
    <ExpenseClaimForm
      expenseType={expenseType}
      project = {project}
      addExpenseData={handleAddExpense}
      setExpense={setExpense}
    />
    <div className="submit">
      <Button Data={"Submit"} onClick={handleSubmit}/>
    </div>
  </div>
);
}

export default ExpenseClaimAdd;
