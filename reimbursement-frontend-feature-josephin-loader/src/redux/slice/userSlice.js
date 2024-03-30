import { createSlice } from "@reduxjs/toolkit";
const initialState = { user: [] };

export const UserSlice = createSlice({
  name: "UserSlice",
  initialState: {
    user: [],
  },
  reducers: {
    setUser: (state, action) => {
      state.user = action.payload;
    },
    clearUser: (state) => {
      state.user = null;
    },
  },
});
export const { setUser, clearUser } = UserSlice.actions;
export default UserSlice.reducer;
