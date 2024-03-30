import { createSlice } from "@reduxjs/toolkit";
const initialState = { expense: null };

const ExpenseSlice = createSlice({
  name: "ExpenseSlice",
  initialState: {
    expense: null,
  },
  reducers: {
    setExpense: (state, action) => {
      state.expense = action.payload;
    },
    clearExpense: (state) => {
      state.expense = null;
    },
  },
});
export const { setExpense, clearExpense } = ExpenseSlice.actions;
export default ExpenseSlice.reducer;
