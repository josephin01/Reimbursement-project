import { combineReducers, configureStore } from "@reduxjs/toolkit";
import {persistReducer, persistStore} from "redux-persist"
import authSlice from "./slice/authSlice";
import ExpenseSlice from "./slice/expenseSlice";
import userSlice from "./slice/userSlice";
import expenseClaimSlice from "./slice/expenseClaimSlice";
import TeamTravelExpenseSlice from "./slice/teamTravelExpenseSlice";
import teamExpenseClaim from "./slice/teamExpenseClaim";
import ExpenseClaimDetailSlice from "./slice/expenseClaimDetailSlice";
import storage from "redux-persist/lib/storage";


const persistConfig = {
  key: "root",
  storage,
  // blacklist:['login']
};

const rootReducer = combineReducers({
  auth: authSlice,
  claim: ExpenseSlice,
  teamTravelExpense:TeamTravelExpenseSlice,
  user:userSlice,
  expenseClaim:expenseClaimSlice,
  teamExpenseClaim:teamExpenseClaim,
  expenseClaimDetail:ExpenseClaimDetailSlice

});

const persistedReducer = persistReducer(persistConfig, rootReducer);


export const Store = configureStore({
  reducer: persistedReducer,
});

export const persistor = persistStore(Store)

