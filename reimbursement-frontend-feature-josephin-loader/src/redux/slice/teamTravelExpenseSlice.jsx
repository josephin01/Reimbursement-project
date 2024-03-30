import { createSlice } from "@reduxjs/toolkit";
const initialState = { teamTravelExpense: null };

const TeamTravelExpenseSlice = createSlice({
  name: "TeamTravelExpenseSlice",
  initialState,
  reducers: {
    setTeamTravelExpense: (state, action) => {
      state.teamTravelExpense = action.payload;
    },
  },
});
export const { setTeamTravelExpense } = TeamTravelExpenseSlice.actions;
export default TeamTravelExpenseSlice.reducer;
