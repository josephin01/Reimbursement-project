import React,{createContext,useState} from 'react'

const AppContext = createContext();

function ContextProvider({children}) {
    const [tableData,setTableData]=useState({})
    const [viewData,setViewData]=useState({})
    const [selectedList,setSelectedList]=useState({})
    const [addData,setAddData]=useState({})
    const [travelFormStatus,setTravelFormStatus]=useState("")
  return (
    <AppContext.Provider
    value={{
        tableData:[tableData,setTableData],
        viewData:[viewData,setViewData],
        selectedList:[selectedList,setSelectedList],
        addData:[addData,setAddData],
        travelFormStatus:[travelFormStatus,setTravelFormStatus]
    }}>{children}</AppContext.Provider>
  )
}

export {AppContext,ContextProvider}