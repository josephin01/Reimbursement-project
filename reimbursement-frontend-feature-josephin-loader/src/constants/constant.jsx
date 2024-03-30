// export const employeeTravelExpenseTable=["Date"]
// export const expenseTypeTable=[{label:"Status",key:"status"},{label:"Form Type",key:"formType"},{label:"Expense Name",key:"expenses"},{label:"EmployeeName",key:"employee"},]
// export const travelFormHeaders=[
// {label:"Purpose",key:"purposeOfVisit"},
// {label:"Date",key:"applyDate"},
// {label:"Date Of Travel",key:"travelDate"}]


export const purposeTable=[{label:"Purpose Name",key:'purposes'}]
export const travelFormTable = [
    {label:"Date",key:"date"},
    {label:"Project Name",key:"projectName"},
    {label:"Status",key:"status"},{label:"Purpose",key:"purpose"},
    {label:"Date Of Travel",key:"dateOfTravel"}
]


export const employeeTravelExpenseTable=["Date"]
export const travelFormHeaders=[
    {label:"Purpose",key:"purpose"},
    {label:"Date",key:"applyDate"},
    {label:"Date Of Travel",key:"dateOfTravel"},
    {label:"Colleague Name",key:"colleagueDetails"},
    {label:"No. of People",key:"numberOfPeople"},
    {label:"Project Name",key:"projectName"},
    {label:"Manager Name",key:"managerName"},
    {label:"Project Scope",key:"projectScope"},
    {label:"Employee Name",key:"employeeName"}
]

export const teamsTravelFormHeaders = [
    {label:"Purpose",key:"purpose"},
    {label:"Date",key:"applyDate"},
    {label:"Date Of Travel",key:"dateOfTravel"},
    {label:"Colleague Name",key:"colleagueDetails"},
    {label:"No. of People",key:"numberOfPeople"},
    {label:"Project Name",key:"projectName"},
    {label:"Manager Name",key:"managerName"},
    {label:"Project Scope",key:"projectScope"},
    {label:"Employee Name",key:"employeeName"}
]

export const expenseHeaders=[
    {label:"Expense Type",key:"expenseType"},
    {label:"Expense Amount",key:"expenseAmount"},
    {label:"Expense Date",key:"expenseDate"},
    {label:"Remarks",key:"expenseDescription"},
]

export const expenseClaimStatusCardsHeaders=[
    {label:"Expense Type",key:"expenseType"},
    {label:"Expense Amount",key:"expenseAmount"},
    {label:"Expense Date",key:"applyDate"},
    {label:"Expense Status",key:"expenseStatus"},
]


export const expenseTableHeader=[
    {label:"Date",key:"expenseDate"},
    {label:"Project Name",key:"projectName"},
    // {label:"Status",key:"expenseStatus"},
    {label:"Amount",key:"expenseAmount"}
]
export const teamsExpenseTableHeader=[
    {label:"Employee Name",key:"firstName"},
    {label:"Date",key:"expenseDate"},
    {label:"Project Name",key:"projectName"},
    {label:"Status",key:"expenseStatus"},
    {label:"Amount",key:"expenseAmount"}
]
export const expenseClaimCards = {
    Expense_Claim:"Expense Claim"
}
export const travelFormCards = {
    Travel_Form:"Travel Form"
}
export const deleteComponent = {
    Delete:"Delete",
}
export const report ={
    Employee_Name:"Employee Name",
    Expense_Type:"Expense Type",
    Project_Name:"Project Name",
    Status:"Status",
    Report_Period:"Report Period",
    Amount:"Amount",
    From_Date:"From Date",
    To_Date:"To Date",
    Minimum:"Minimum",
    Maximum:"Maximum",
    Report_Type:"Report_Type",
}

export const teamsTravelData =[
    {label:"Employee",key:"employeeName"},
    {label:"Date",key:"date"},
    {label:"Project Name",key:"projectName"},
    {label:"Status",key:"status"},
    {label:"Purpose",key:"purpose"},
    {label:"Date Of Travel",key:"dateOfTravel"}
]

export const expenseTableHeaders =[
    {label:"Date",key:"applyDate"},
    {label:"Expense Type",key:"expenseType"},
    {label:"Status",key:"expenseStatus"},
    {label:"Amount",key:"expenseAmount"}
]




export const travelFormViewData ={
    travelForm : "Travel Form",
    addExpense:"Add Expense",
    totalAmount:"Total Amount",
    saveAndSendButton:"Save and Send",
    rejected:"Rejected"
}

export const travelFormTableData ={
    travelForm : "Travel Form"
}

export const reportPage ={
    report : "Report",
    employeeName:"Employee Name",
    expenseType:"Expense Type",
    projectName:"Project Name",
    status:"Status",
    reportPeriod:"Report Period",
    amount:"Amount",
    startDate:"From Date",
    endDate:"To Date",
    minimum:"Minimum",
    maximum:"Maximum",
    reportType:"Report Type"

}
export const PurposePageData = {
    addPurpose : "Add Purpose",
    purpose:"Purpose",
    submit:"Submit",
    cancel:"Cancel",
    editPurpose:"Edit Purpose",
    save:"Save"
}

export const layoutData = {
    teams:"Teams",
    travelForm:"Travel Form"

}

export const tableData = {
    action:"Action"
}

export const statusFlowData = {
    formSubmittedOn:"Form Submitted On",
    formApprovedBy:"Form Approved By",
    formRejectedBy:"Form Rejected By",
    billAddedOn:"Bill Added On",
    billApprovedBy:"Bill Approved By",
    billRejectedBy:"Bill Rejected By",
    formApprovedByAdmin:"Form Approved By Admin",
    formRejectedByAdmin:"Form Rejected By Admin"
}
export const sideBarData = {
    hi:"Hi"
}

export const travelFormData = {
    purpose:"Purpose",
    selectPurpose:"Select Purpose",
    date:"Date",
    dateOfTravel:"Date Of Travel",
    colleagueName:"Colleague Name",
    noOfPeople:"No. Of People",
    projectName:"Project Name",
    selectProject:"Select Project",
    managerName:"Manager Name",
    selectManager:"Select Manager",
    projectScope:"Project Scope"
}

export const expenseClaim = {
    expenseType:"Expense Type",
    selectExpenseType:"Select Expense Type",
    expenseAmount:"Expense Amount",
    expenseDate:"Expense Date",
    colleagueName:"Colleague Name",
    remarks:"Remarks",
    addBill:"Add Bill"
}

export const deleteData = {
    delete:"Delete"
}
export const batchtableData = [
  { label: "Employee Name", key: "empName" },
  { label: "Employee Id", key: "empId" },
  { label: "Date", key: "date" },
  { label: "Reference", key: "reference" },
];

export const batchForm = [
  { label: "Purpose of Visit", key: "purposeOfVisit" },
  { label: "Apply Date", key: "applyDate" },
  { label: "Colleague Details", key: "colleagueDetails" },
  { label: "Number of People", key: "numberOfPeople" },
  { label: "Travel Date", key: "travelDate" },
  { label: "Project Scope", key: "projectScope" },
  { label: "Project Name", key: "projectName" },
  { label: "Manager Name", key: "managerName" },
];

export const expensebillForm = [
  { label: "Expense Type", key: "expenseType" },
  { label: "Expense Description", key: "expenseDescription" },
  { label: "Expense Date", key: "expenseDate" },
  { label: "Expense Amount", key: "expenseAmount" },
  { label: "Apply Date", key: "applyDate" },
];

export const projectTable = [
  {label: "Manager Name", key: "managers"},
  {label: "Project Name", key: "projectName"}

];

export const teamExpenseClaim =[
{ label: "employee Name", key: "employeeName" },
  { label: "Status", key: "expenseStatus" },
  { label: "Date", key: "applyDate" },
  { label: "Project Name", key: "projectName" },
  {label:"Amount",key:"expenseAmount"}, 

];

export const teamExpensedisplayFormData = [
    // {label:"Date", key:'applyDate'},
    // {label:"colleague", key:'colleagueDetails'},
    // {label:"Amount", key:'expenseAmount'},
    // {label:"expenseDate", key:'expenseDate'},
    // {label:"Description", key:'expenseDescription'},
    // {label:"expenseType", key:'expenseType'},
    // {label:"managerName", key:'managerName'},
    // {label:"projectName", key:'projectName'},
    { label: "employee Name", key: "employeeName" },
  { label: "Status", key: "expenseStatus" },
  { label: "Date", key: "applyDate" },
  { label: "Project Name", key: "projectName" },
  {label:"Amount",key:"expenseAmount"},     
];

export const displayFormData = [
    {label:"Date", key:'applyDate'},
    {label:"Colleague", key:'colleagueDetails'},
    {label:"Amount", key:'expenseAmount'},
    {label:"Expense Date", key:'expenseDate'},
    {label:"Description", key:'expenseDescription'},
    {label:"Expense Type", key:'expenseType'},
    {label:"Manager Name", key:'managerName'},
    {label:"Project Name", key:'projectName'},    
   
];

export const teamExpenseFormData = [
    {label:"Employee_Name", key:'Employee_Name'},
    {label:"Date", key:'Date'},
    {label:"Amount", key:'Amount'},
    {label:"Status", key:'Status'},
    {label:"Project_Name", key:'Project_Name'},  
]

export const expenseClaimData = [{label:"Date",key:"applyDate"},
{label:"Expense Type",key:"expenseType"},
{label:"Status",key:"expenseStatus"},
{label:"Amount",key:"expenseAmount"}];

export const batchFormData = {
  From: "From",
  To: "To",
  EmployeeName: "Employee Name",
  Remarks: "Remarks",
}

export const expenseTypeTable = [
    {label:'Expenses',key:'expenses'},
    {label:'Form Type',key:'formType'}
]


export const peopleTable = [
    {label:'Employee Name',key:'employeeName'},
    {label:'Employee ID',key:'empId'},
    {label:'Phone',key:'phone'},
    {label:'Role',key:'role'},
    {label:'DOB',key:'dob'}
]

export const expenseClaimAdminHeaders =[
    
    {label:'Expense Type',key:'expenseType'},
    {label:'Expense Status',key:'expenseStatus'},
    {label:'Date',key:'applyDate'},
    {label:'Amount',key:'expenseAmount'},
]

export const expenseTypeData = {
    Expenses:'Expenses',
    FormType:'Form Type',
}

export const employeeEditFormData = {

    EmployeeName:'Employee Name',
    Employee_ID:'Employee ID',
    Employee_Dob:'DOB',
    Employee_Role:'ROle',
    Employee_Contact:'Contact',

}

export const constant = {
    login_gretting: "Hello There",
    login_quotes:
      "Control your expenses better than your competition. This is where you can always find the competitive advantage",
    login_quotes_author: "Sam Walton...",
  };
  export const employeeForm = {
    formHeader: "Employee Details",
    formDescription: "Enter Your Details As They Appear On Your Claims!",
    firstName: "First Name",
    lastName: "Last Name",
    contact: "Contact",
    email: "Email",
    dob: "DOB",
    empId: "EmployeeID",
    action:"Action",
    role:"Role",
    name:'Name',
  };

export const wishes = {
    morning:'Good Morning',
    afternoon:'Good Afternoon',
    evening:'Good Evening'
}

export const travelFormStatus=['ALL','FORM_APPROVED','FORM_REJECTED','FORM_PENDING'];

export const travelExpenseClaimStatus=['ALL','PENDING','MANAGER_APPROVED','MANAGER_REJECTED','ADMIN_APPROVED','ADMIN_REJECTED']

export const expenseClaimStatus=['ALL','PENDING','MANAGER_APPROVED','MANAGER_REJECTED','ADMIN_APPROVED','ADMIN_REJECTED']

export const adminTravelFormRole =[
    {label:'Date',key:'Apply Date'},
    {label:'Employee Name',key:'Employee Name'},
    {label:'status',key:'Status'},
    {label:'Purpose',key:'Purpose of Visit'},
    {label:'Date Of Travel',key:'Travel Date'},

]

export const adminTravelFormStatus =[
    {label:'Date',key:'date'},
    {label:'Project Name',key:'projectName'},
    {label:'status',key:'status'},
    {label:'Purpose',key:'purpose'},
    {label:'Date Of Travel',key:'dateOfTravel'},

]

