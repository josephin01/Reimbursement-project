package com.reimbursement.project.constant;

public class ApiConstant {




    private ApiConstant() {
    }
    public static final String ADMIN="/api/v1/admin";
    public static final String EXPENSE_TYPE="/expensetype";
    public static final String FORM_TYPE="/formtype";
    public static final String EXPENSE_TYPE_FORM_TYPE="/getexpensebyformtype";
    public static final String ALL_EXPENSE_TYPE="/getallexpensetype";
    public static final String PURPOSE_OF_VISIT="/purposeofvisit";
    public static final String REPORT="/report";
    public static final String ROLES="/api/v1/roles";
    public static final String ALL_EMPLOYEE="allemployees";
    public static final String BATCH = "/api/v1/batches";
    public static final String BATCH_ID = "/{id}";
    public static final String EMPLOYEES = "/api/v1/employees";
    public static final String EMPLOYEE_ALL = "/all";
    public static final String BATCH_EMPLOYEE_ID = "/id/{empId}";
    public static final String  BATCH_DELETE_EMPLOYEE_ID = "/delete/{empId}";
    public static final String BATCH_SEARCH = "/search";
    public static final String EXPENSE_CLAIMS = "/api/v1/expense-claims";
    public static final String EXPENSE_CLAIMS_ID = "/{id}";
    public static final String EXPENSE_CLAIMS_UPDATE = "{id}";
    public static final String EXPENSE_CLAIMS_ADMIN_VIEW_BY_STATUS = "/admin-view/status/{status}";
    public static final String EXPENSE_CLAIMS_APPROVAL = "/approval";
    public static final String EXPENSE_CLAIMS_COUNT = "/count";
    public static final String EXPENSE_CLAIMS_HISTORY = "/history/{empId}/{status}";
    public static final String  EXPENSE_CLAIMS_ADMIN_VIEW_ALL = "/admin/view";
    public static final String EXPENSE_CLAIMS_MANAGER_VIEW_BY_STATUS = "/manager/view/{managerId}/{status}";
    public static final String EXPENSES_AMOUNT_SPENT = "/amount_spent";
    public static final String AUTH_APIS = "/api/v1/auth";
    public static final String AUTH_ADD_DETAILS = "addUserDetails";
    public static final String AUTH_REGENERATE_TOKEN = "regenerateToken";
    public static final String AUTH_LOGOUT = "logout";
    public static final String MANAGER = "/api/v1/manager";
    public static final String NOTIFICATION = "/api/v1/notification";
    public static final String NOTIFICATION_EMPLOYEE = "employee/{empId}";
    public static final String NOTIFICATION_UPDATE_EMPLOYEE = "updateEmployeeNotification";
    public static final String NOTIFICATION_MANAGER = "manager/{managerId}";
    public static final String NOTIFICATION_ADMIN = "admin";
    public static final String PROJECTS = "/api/v1/projects";
    public static final String PROJECTS_ID = "/{id}";
    public static final String PROJECTS_ALL = "/all";
    public static final String PROJECTS_ALL_DROPDOWN = "/allProjects";
    public static final String PROJECTS_UPDATE = "/{id}";
    public static final String PROJECTS_DELETE = "/delete/{id}";
    public static final String PROJECTS_REPORT = "/report";
    public static final String TRAVEL_FORM = "/api/v1/travelForm/";
    public static final String TRAVEL_FORM_GET = "{travelFormId}";
    public static final String TRAVEL_FORM_EMPLOYEE_BY_STATUS = "employee/{employeeId}/{status}";
    public static final String TRAVEL_FORM_MANAGER_BY_STATUS = "manager/{managerId}/{status}";
    public static final String TRAVEL_FORM_ADMIN_BY_STATUS = "admin/{status}";
    public static final String TRAVEL_FORM_UPDATE_STATUS = "updateStatus/{travelFormId}";
    public static final String TRAVEL_FORM_BY_STATUS = "status/{status}";
    public static final String EXPENSES="/api/v1/expenses";
    public static final String TRAVEL_FORM_ID="/travel_form_id/{id}";
    public static final String BATCH_FILTERS="/filters";
    public static final String BATCH_HISTORY="/batches_history";
    public static final String COLLEAGUES="/colleagues";
    public static final String EXPENSE_ID="/expense_id/{id}";
    public static final String APPROVE_TRAVEL_FORM_ID="/approve/travel_form_id/{id}";
    public static final String EXPENSES_STATUS="/status";
    public static final String ALL_EXPENSE_STATUS="/getexpensestatus";
    public static final String EXPENSE_COUNT_EMPLOYEE_ID="/expenses_count/{empId}";
    public static final String BILLS_COUNT="/bills_count";
    public static final String TRAVEL_FORM_COUNT="/travel_form_count";
    public static final String EMPLOYEE_ID_NAME="/getemployeeid&name";
    public static final String TRAVEL_FORM_ADMIN = "/getTravelFormByRole/{role}";
    public static final String EXPENSE_CLAIMS_BY_ROLE ="/getByRole/{role}" ;
    public static final String MANAGER_ROLES = "/{roles}";
    public static final String EXPENSE_BY_ROLE ="/getbyrole/{role}" ;
    public static final String EXPENSE_CLAIMS_EMPLOYEE_ID_COUNT="/getexpenseclaimscount/{empId}";

}
