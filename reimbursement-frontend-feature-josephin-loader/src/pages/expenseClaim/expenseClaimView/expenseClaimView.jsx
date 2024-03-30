import React,{useContext} from 'react'
import { AppContext } from '../../../context/context'
import ExpenseClaimForm from '../../../components/forms/expenseClaim/expenseClaimForm';
import DisplayForm from '../../../components/forms/displayform/displayform';
import { displayFormData } from '../../../constants/constant';

function ExpenseClaimView() {
  const { viewData } = useContext(AppContext);
  const [viewDataValue, setViewDataValue] = viewData;
  return (
    <div><DisplayForm
    Data={viewDataValue}
    headers={displayFormData}
    /></div>
  )
}

export default ExpenseClaimView