package com.reimbursement.project.service.impl;

import com.reimbursement.project.constant.Constant;
import com.reimbursement.project.dto.*;
import com.reimbursement.project.entity.*;
import com.reimbursement.project.entity.Enum.*;
import com.reimbursement.project.exception.InvalidDataException;
import com.reimbursement.project.exception.ResourceNotFoundException;
import com.reimbursement.project.repository.service.*;
import com.reimbursement.project.service.NotificationService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final EmployeeDetailsRepoService employeeDetailsRepoService;
    private final TravelFormRepoService travelFormRepoService;
    private final NotificationRepoService notificationRepoService;
    private final ExpenseTypeRepoService expenseTypeRepoService;
    private final ExpenseClaimsRepoService expenseClaimsRepoService;
    private final JavaMailSender javaMailSender;


    @Override
    public ResponseEntity<ApiResponseDto> getEmployeeTravelFormNotification(Long empId) {

        Optional<EmployeeDetails> employeeDetails = employeeDetailsRepoService.findByEmpId(empId);

        if(employeeDetails.isEmpty()){
            throw new ResourceNotFoundException(Constant.EMPLOYEE_ID_NOT_FOUND);

        }

        List<TravelForm> travelForms = travelFormRepoService.findByEmpId(employeeDetails.get().getId());
        List<EmployeeNotificationDto> employeeNotificationResponse = new ArrayList<>();
        for(TravelForm travelFormData : travelForms){
            Notification notification = travelFormData.getNotification();
            if(notification.getEmployeeNotificationStatus() == NotifyStatus.SENT && (travelFormData.getTravelFormStatus() == TravelFormStatus.FORM_APPROVED || travelFormData.getTravelFormStatus() == TravelFormStatus.FORM_REJECTED)){
                EmployeeNotificationDto employeeNotification = EmployeeNotificationDto.builder()
                        .notificationId(notification.getId())
                        .formType(notification.getNotificationFormType())
                        .status(String.valueOf(travelFormData.getTravelFormStatus()))
                        .build();
                employeeNotificationResponse.add(employeeNotification);
            }
        }

        List<ExpenseClaims> expenseClaims = expenseClaimsRepoService.findAllByEmployee(employeeDetails.get());
        for(ExpenseClaims expenseClaimsData : expenseClaims){
            Notification notification = expenseClaimsData.getNotification();
            if(notification.getEmployeeNotificationStatus() == NotifyStatus.SENT && (expenseClaimsData.getExpenseStatus() == ExpenseStatus.MANAGER_APPROVED || expenseClaimsData.getExpenseStatus() == ExpenseStatus.MANAGER_REJECTED )){
                EmployeeNotificationDto employeeNotification = EmployeeNotificationDto.builder()
                        .notificationId(notification.getId())
                        .formType(notification.getNotificationFormType())
                        .status(String.valueOf(expenseClaimsData.getExpenseStatus()))
                        .build();
                employeeNotificationResponse.add(employeeNotification);
            }
            if(notification.getEmployeeAdminNotificationStatus() == NotifyStatus.SENT && (expenseClaimsData.getExpenseStatus() == ExpenseStatus.ADMIN_APPROVED || expenseClaimsData.getExpenseStatus() == ExpenseStatus.ADMIN_REJECTED )){
                EmployeeNotificationDto employeeNotification = EmployeeNotificationDto.builder()
                        .notificationId(notification.getId())
                        .formType(notification.getNotificationFormType())
                        .status(String.valueOf(expenseClaimsData.getExpenseStatus()))
                        .build();
                employeeNotificationResponse.add(employeeNotification);
            }
        }
        ApiResponseDto apiResponse = ApiResponseDto.builder()
                .httpStatus(HttpStatus.OK)
                .message(Constant.NOTIFICATION_DATA_SENT)
                .data(employeeNotificationResponse)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @Override
    public ResponseEntity<ApiResponseDto> updateNotificationStatus(ChangeNotificationStatusDto changeNotificationData) {

        List<UpdateNotificationStatusDto> employeeIds = changeNotificationData.getUpdateNotificationStatus();
        if(employeeIds.isEmpty()){
            throw new InvalidDataException(Constant.EMPTY_INPUT);
        }

        for(UpdateNotificationStatusDto employeeNotification : employeeIds){
            Optional<Notification> notification = notificationRepoService.findById(employeeNotification.getNotificationId());
            if(notification.isEmpty()){
                throw new ResourceNotFoundException(Constant.NOTIFICATION_ID_NOT_FOUND);
            }

            Notification updateNotification = notification.get();
            switch (changeNotificationData.getRole()) {
                case Constant.EMPLOYEE:
                    updateNotification.setEmployeeNotificationStatus(NotifyStatus.SEEN);
                    break;
                case Constant.MANAGER:
                    updateNotification.setManagerNotificationStatus(NotifyStatus.SEEN);
                    break;
                case Constant.ADMIN:
                    updateNotification.setEmployeeAdminNotificationStatus(NotifyStatus.SEEN);
                    updateNotification.setAdminNotificationStatus(NotifyStatus.SEEN);
                    break;
                default:
                    throw new InvalidDataException(Constant.INVALID_ROLE);
            }
            notificationRepoService.saveData(updateNotification);
        }

        ApiResponseDto apiResponse = ApiResponseDto.builder()
                .httpStatus(HttpStatus.OK)
                .message(Constant.NOTIFICATION_STATUS_UPDATED)
                .data(null)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @Override
    public ResponseEntity<ApiResponseDto> getManagerNotification(Long managerId) {

        if(managerId == null){
            throw new InvalidDataException(Constant.EMPTY_INPUT);
        }

        Optional<EmployeeDetails> employeeDetails = employeeDetailsRepoService.findByEmpId(managerId);
        if(employeeDetails.isEmpty()){
            throw new ResourceNotFoundException(Constant.MANAGER_ID_NOT_FOUND);
        }

        EmployeeDetails employeeData = employeeDetails.get();
        if(Boolean.FALSE.equals(employeeData.getIsManager())){
            throw new ResourceNotFoundException(Constant.NOT_MANAGER);
        }

        List<TravelForm> travelForms = travelFormRepoService.findByManagerId(employeeData.getManagers().getId());
        List<ManagerNotificationResponseDto> managerResponse = new ArrayList<>();
        for(TravelForm travelForm : travelForms){
            Optional<EmployeeDetails> employee = employeeDetailsRepoService.findByEmpId(travelForm.getEmployeeDetails().getEmpId());

            if(employee.isEmpty()){
                throw new ResourceNotFoundException(Constant.EMPLOYEE_ID_NOT_FOUND);
            }

            Notification notification = travelForm.getNotification();
            if(notification.getManagerNotificationStatus() == NotifyStatus.SENT){
                ManagerNotificationResponseDto managerNotificationResponse = ManagerNotificationResponseDto.builder()
                        .formType(notification.getNotificationFormType())
                        .name(employee.get().getFirstName() + " " + employee.get().getLastName())
                        .empId(employee.get().getEmpId())
                        .notificationId(notification.getId())
                        .build();
                managerResponse.add(managerNotificationResponse);
            }
        }

        List<ExpenseClaims> expenseClaims = expenseClaimsRepoService.findAllByManagerId(employeeData.getManagers().getId());
        for(ExpenseClaims expenseClaim : expenseClaims){
            Optional<EmployeeDetails> employee = employeeDetailsRepoService.findByEmpId(expenseClaim.getEmployeeDetails().getEmpId());

            if(employee.isEmpty()){
                throw new ResourceNotFoundException(Constant.EMPLOYEE_ID_NOT_FOUND);
            }

            ManagerNotificationResponseDto managerNotificationResponse = ManagerNotificationResponseDto.builder()
                    .formType(expenseClaim.getNotification().getNotificationFormType())
                    .name(employee.get().getFirstName() + " " + employee.get().getLastName())
                    .empId(employee.get().getEmpId())
                    .notificationId(expenseClaim.getNotification().getId())
                    .build();
            managerResponse.add(managerNotificationResponse);
        }
 
        ApiResponseDto apiResponse = ApiResponseDto.builder()
                .httpStatus(HttpStatus.OK)
                .message(Constant.NOTIFICATION_DATA_SENT)
                .data(managerResponse)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @Override
    public ResponseEntity<ApiResponseDto> getAdminNotification() {

        List<TravelForm> travelForms = travelFormRepoService.findAllTravelForm();
        List<AdminNotificationResponseDto> adminNotificationResponse = new ArrayList<>();
        for(TravelForm travelForm : travelForms){
            Optional<EmployeeDetails> employee = employeeDetailsRepoService.findByEmpId(travelForm.getEmployeeDetails().getEmpId());

            if(employee.isEmpty()){
                throw new ResourceNotFoundException(Constant.EMPLOYEE_ID_NOT_FOUND);

            }

            Notification notification = travelForm.getNotification();
            if(notification.getAdminNotificationStatus() == NotifyStatus.SENT){
                AdminNotificationResponseDto notificationResponse = AdminNotificationResponseDto.builder()
                        .formType(notification.getNotificationFormType())
                        .name(employee.get().getFirstName() + " " + employee.get().getLastName())
                        .empId(employee.get().getEmpId())
                        .notificationId(notification.getId())
                        .build();
                adminNotificationResponse.add(notificationResponse);
            }
        }

        List<ExpenseClaims> expenseClaims = expenseClaimsRepoService.findAllExpenseClaimNotification();
        for(ExpenseClaims expenseClaim : expenseClaims){
            Optional<EmployeeDetails> employee = employeeDetailsRepoService.findByEmpId(expenseClaim.getEmployeeDetails().getEmpId());

            if(employee.isEmpty()){
                throw new ResourceNotFoundException(Constant.EMPLOYEE_ID_NOT_FOUND);
            }

            AdminNotificationResponseDto adminNotification = AdminNotificationResponseDto.builder()
                    .formType(expenseClaim.getNotification().getNotificationFormType())
                    .name(employee.get().getFirstName() + " " + employee.get().getLastName())
                    .empId(employee.get().getEmpId())
                    .notificationId(expenseClaim.getNotification().getId())
                    .build();
            adminNotificationResponse.add(adminNotification);
        }

        ApiResponseDto apiResponse = ApiResponseDto.builder()
                .httpStatus(HttpStatus.OK)
                .message(Constant.NOTIFICATION_DATA_SENT)
                .data(adminNotificationResponse)
                .build();

        return ResponseEntity.ok(apiResponse);

    }

    @Override
    @Scheduled(cron = "0 0 9 1 * *")
    public void notifyCertification() throws MessagingException {
        Optional<ExpenseType> expenseType =expenseTypeRepoService.isExpenseTypePresent("Certification", FormType.EXPENSE_CLAIMS);
        if(expenseType.isPresent()) {
            Long expenseTypeId = expenseType.get().getId();
            List<ExpenseClaims> expenseClaims = expenseClaimsRepoService.findByExpenseId(expenseTypeId);
            LocalDate currentDate = LocalDate.now();
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setSubject("Reminder:Expense Claim on Certification");
            StringBuilder text = new StringBuilder("The certifications claimed by the employee for this year are:\n");
            for (ExpenseClaims expense : expenseClaims) {
                LocalDate applyDate = expense.getApplyDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                long month = ChronoUnit.MONTHS.between(applyDate, currentDate);
                long days=ChronoUnit.DAYS.between(applyDate,currentDate);
                if (month <= 12) {
                    Long id = expense.getEmployeeDetails().getEmpId();
                    String name = expense.getEmployeeDetails().getFirstName() + " " + expense.getEmployeeDetails().getLastName();
                    float expenseAmount = expense.getExpenseAmount();
                    String details = "\nEmployee Id: " + id + "\nEmployee Name: " + name + "\nApplied Date: " + applyDate + "\nExpense Amount: " + expenseAmount + "\nMonths: " + month + "\nDays: "+ days;
                    text.append(details);
                }
            }
            helper.setText(text.toString());
            helper.setTo("ragupathy.harismita@divum.in");
            helper.setFrom("harismitaragupathy@gmail.com");
            javaMailSender.send(message);
        }
    }
}
