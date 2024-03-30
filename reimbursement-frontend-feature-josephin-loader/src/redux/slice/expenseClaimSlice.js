import { createSlice } from "@reduxjs/toolkit";
const initialState = { expenseClaim: null };

const ExpenseClaimSlice = createSlice({
  name: "ExpenseClaimSlice",
  initialState: {
    expenseClaim: null,
  },
  reducers: {
    setExpenseClaim: (state, action) => {
      state.expenseClaim = action.payload;
    },
    clearExpenseClaim: (state) => {
      state.expenseClaim = null;
    },
  },
});
export const { setExpenseClaim, clearExpenseClaim } = ExpenseClaimSlice.actions;
export default ExpenseClaimSlice.reducer;
