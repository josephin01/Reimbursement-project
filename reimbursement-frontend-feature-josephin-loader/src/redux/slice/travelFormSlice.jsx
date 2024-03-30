import { createSlice } from "@reduxjs/toolkit";
const initialState = { expense: null };

const ExpenseSlice = createSlice({
  name: "TravelFormSlice",
  initialState: {
    expense: null,
  },
  reducers: {
    setTravelForm: (state, action) => {
      state.expense = action.payload;
    },
    clearTravelForm: (state) => {
      state.expense = null;
    },
  },
});
export const { setExpense, clearExpense } = TravelFormSlice.actions;
export default TravelFormSlice.reducer;