import { createSlice } from "@reduxjs/toolkit";

const teamExpenseClaim = createSlice({
  name: "teamExpense",
  initialState: {
    data: null,
  },
  reducers: {
    setTeamExpense: (state, action) => {
      state.data = action.payload;
      console.log(state);
    },
    clearExpense: (state) => {
      state.data = null;
    },
  },
});
export const { setTeamExpense, clearExpense } = teamExpenseClaim.actions;
export default teamExpenseClaim.reducer;
