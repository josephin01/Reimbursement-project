import { createSlice } from "@reduxjs/toolkit";
const initialState = { colleague: null,project:null,expenseType:null,manager:null };

const ExpenseClaimDetailSlice = createSlice({
  name: "ExpenseClaimDetailSlice",
  initialState: {
    colleague: null,project:[],expenseType:[],manager:null 
  },
  reducers: {
    setExpenseType: (state, action) => {
      state.expenseType = action.payload;
    },
    setColleague:(state,action)=>{
        state.colleague = action.payload;
    },
    setProject:(state,action)=>{
        state.project = action.payload;
    },
    setManager:(state,action)=>{
        state.manager = action.payload;
    }
  },
});
export const { setExpenseType,setColleague,setManager,setProject } = ExpenseClaimDetailSlice.actions;
export default ExpenseClaimDetailSlice.reducer;
