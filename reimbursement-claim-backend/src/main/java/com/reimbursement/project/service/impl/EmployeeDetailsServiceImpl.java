package com.reimbursement.project.service.impl;

import com.reimbursement.project.constant.Constant;
import com.reimbursement.project.dto.*;
import com.reimbursement.project.entity.EmployeeDetails;
import com.reimbursement.project.entity.Roles;
import com.reimbursement.project.entity.Session;
import com.reimbursement.project.exception.AlreadyLoggedOutException;
import com.reimbursement.project.exception.JwtException;
import com.reimbursement.project.exception.ResourceNotFoundException;
import com.reimbursement.project.exception.UserAlreadyExistsException;
import com.reimbursement.project.repository.service.EmployeeDetailsRepoService;
import com.reimbursement.project.repository.service.SessionRepoService;
import com.reimbursement.project.service.EmployeeDetailsService;
import com.reimbursement.project.service.JwtServices;
import com.reimbursement.project.service.OAuthUserInfoService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeDetailsServiceImpl implements EmployeeDetailsService {

    private final EmployeeDetailsRepoService employeeDetailsRepoService;
    private final OAuthUserInfoService oAuthUserInfoService;
    private final JwtServices jwtServicesImplementation;
    private final SessionRepoService sessionRepoService;

    @Override
    public ResponseEntity<ApiResponseDto> getUserIfExists(LoginRequestDto token) {

        try {
            ApiResponseDto apiResponseDto;
            Map<String, Object> userInfo = oAuthUserInfoService.getUserInfo(token.getAccessToken());
            String userEmail = String.valueOf(userInfo.get(Constant.EMAIL));

            Optional<EmployeeDetails> employeeDetails = employeeDetailsRepoService.findByEmail(userEmail);
            if (employeeDetails.isEmpty()) {
                apiResponseDto = ApiResponseDto.builder()
                        .httpStatus(HttpStatus.OK)
                        .message(Constant.USER_NOT_FOUND)
                        .data(null)
                        .build();
                return ResponseEntity.ok(apiResponseDto);
            }
            EmployeeDetails details = employeeDetails.get();
            Session session = Session.builder()
                    .employeeDetails(details)
                    .loginTime(new Timestamp(System.currentTimeMillis()))
                    .build();
            Session savedSession = sessionRepoService.saveSession(session);
            EmployeeDetailsLoginResponseDto employeeDetailsLoginResponseDto = EmployeeDetailsLoginResponseDto.builder()
                    .id(details.getId())
                    .empId(details.getEmpId())
                    .email(details.getEmail())
                    .phone(details.getPhone())
                    .dob(details.getDob())
                    .profile(details.getProfile())
                    .firstName(details.getFirstName())
                    .lastName(details.getLastName())
                    .role(details.getRole().getId())
                    .build();

            String jwtToken = jwtServicesImplementation.getJwtToken(userEmail, savedSession.getId());
            String refreshToken = jwtServicesImplementation.getRefreshToken(userEmail, savedSession.getId());
            LoginResponseDto loginResponse = LoginResponseDto.builder()
                    .accessToken(jwtToken)
                    .refreshToken(refreshToken)
                    .employeeDetails(employeeDetailsLoginResponseDto)
                    .build();

            apiResponseDto = ApiResponseDto.builder()
                    .httpStatus(HttpStatus.OK)
                    .message(Constant.LOGIN_SUCCESSFUL)
                    .data(loginResponse)
                    .build();
            return ResponseEntity.ok(apiResponseDto);
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public ResponseEntity<ApiResponseDto> addUser(UserDataDto userData) {

        boolean isEmailExists = employeeDetailsRepoService.doesEmailExists(userData.getEmail());
        if (isEmailExists) {
            throw new UserAlreadyExistsException(Constant.EMAIL + Constant.ALREADY_EXISTS);
        }
        boolean isEmpIdExists = employeeDetailsRepoService.doesEmpIdExists(userData.getEmpId());

        if (isEmpIdExists) {
            throw new UserAlreadyExistsException(Constant.EMPLOYEE_ID + Constant.ALREADY_EXISTS);
        }

        Roles roles = Roles.builder()
                .id(3L)
                .build();

        EmployeeDetails employeeDetails = EmployeeDetails.builder()
                .firstName(userData.getFirstName())
                .lastName(userData.getLastName())
                .empId(userData.getEmpId())
                .email(userData.getEmail())
                .dob(userData.getDob())
                .phone(userData.getContact())
                .profile(userData.getProfile())
                .isManager(false)
                .role(roles)
                .build();

        EmployeeDetails savedEmployeeDetails = employeeDetailsRepoService.saveUserData(employeeDetails);

        Session session = Session.builder()
                .employeeDetails(savedEmployeeDetails)
                .loginTime(new Timestamp(System.currentTimeMillis()))
                .build();
        Session savedSession = sessionRepoService.saveSession(session);

        EmployeeDetailsLoginResponseDto employeeDetailsLoginResponseDto = EmployeeDetailsLoginResponseDto.builder()
                .id(savedEmployeeDetails.getId())
                .empId(savedEmployeeDetails.getEmpId())
                .email(savedEmployeeDetails.getEmail())
                .phone(savedEmployeeDetails.getPhone())
                .dob(savedEmployeeDetails.getDob())
                .profile(savedEmployeeDetails.getProfile())
                .firstName(savedEmployeeDetails.getFirstName())
                .lastName(savedEmployeeDetails.getLastName())
                .role(savedEmployeeDetails.getRole().getId())
                .build();

        String jwtToken = jwtServicesImplementation.getJwtToken(savedEmployeeDetails.getEmail(), savedSession.getId());
        String refreshToken = jwtServicesImplementation.getRefreshToken(savedEmployeeDetails.getEmail(), savedSession.getId());
        LoginResponseDto loginResponse = LoginResponseDto.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .employeeDetails(employeeDetailsLoginResponseDto)
                .build();
        ApiResponseDto apiResponseDto = ApiResponseDto.builder()
                .httpStatus(HttpStatus.CREATED)
                .message(Constant.USER_CREATED)
                .data(loginResponse)
                .build();

        return ResponseEntity.ok(apiResponseDto);
    }

    @Override
    public ResponseEntity<ApiResponseDto> generateToken(RegenerateTokenRequestDto regenerateTokenRequestDto) {
        try {
            Claims claimsData = jwtServicesImplementation.extractAllDetails(regenerateTokenRequestDto.getRefreshToken());
            Long sid = claimsData.get(Constant.SESSION_ID, Long.class);
            String email = claimsData.get(Constant.EMAIL, String.class);
            Optional<Session> sessionDetails = sessionRepoService.findBySessionId(sid);
            if (sessionDetails.isEmpty()) {
                throw new JwtException(Constant.INVALID_TOKEN);
            }

            Session sessionData = sessionDetails.get();
            if (sessionData.getLogoutTime() != null) {
                throw new JwtException(Constant.SESSION_EXPIRED);
            }

            String jwtToken = jwtServicesImplementation.getJwtToken(email, sid);
            String refreshToken = jwtServicesImplementation.getRefreshToken(email, sid);

            RegenerateTokenResponseDto regenerateTokenResponse = RegenerateTokenResponseDto.builder()
                    .accessToken(jwtToken)
                    .refreshToken(refreshToken)
                    .build();

            ApiResponseDto apiResponse = ApiResponseDto.builder()
                    .httpStatus(HttpStatus.OK)
                    .message(Constant.TOKEN_GENERATED)
                    .data(regenerateTokenResponse)
                    .build();

            return ResponseEntity.ok(apiResponse);
        }catch (ExpiredJwtException e){
            throw new JwtException(Constant.TOKEN_EXPIRED);
        }
        catch (Exception e) {
            throw new ResourceNotFoundException(Constant.INVALID_TOKEN + " " + e.getMessage());
        }
    }
    @Override
    public ResponseEntity<ApiResponseDto> logoutUser(LogoutRequestDto logoutData) {
        try {
            Claims claims = jwtServicesImplementation.extractAllDetails(logoutData.getAccessToken());
            Long sid = claims.get(Constant.SESSION_ID, Long.class);

            Session sessionData = sessionRepoService.findBySessionId(sid)
                    .orElseThrow(() -> new JwtException(Constant.INVALID_TOKEN));

            if (sessionData.getLogoutTime() != null) {
                throw new AlreadyLoggedOutException(Constant.ALREADY_LOGGED_OUT);
            }

            sessionData.setLogoutTime(new Timestamp(System.currentTimeMillis()));
            sessionRepoService.saveSession(sessionData);

            ApiResponseDto apiResponse = ApiResponseDto.builder()
                    .httpStatus(HttpStatus.OK)
                    .message(Constant.LOGGED_OUT)
                    .data(null)
                    .build();

            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            throw new ResourceNotFoundException(Constant.INVALID_TOKEN);
        }
    }
    public ResponseEntity<ApiResponseDto> getAllEmployees() {
        List<EmployeeDetails> employeeDetails = employeeDetailsRepoService.findAllEmployees();

        List<EmployeeDetailsListDto> employeeDetailsList = employeeDetails.stream()
                .filter(employee -> employee.getDeletedAt()==null)
                .map(employee -> new EmployeeDetailsListDto(
                        employee.getId(),
                        employee.getFirstName(),
                        employee.getEmpId(),
                        employee.getPhone(),
                        employee.getRole().getRoleName(),
                        employee.getDob()
                )).toList();

        return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK, Constant.EMPLOYEE_DETAILS_RETRIEVED, employeeDetailsList));
    }

    @Override
    public ResponseEntity<ApiResponseDto> getColleaguesInfo() {
        List<Map<String, Object>> employeeDetails = employeeDetailsRepoService.getColleagues();
        if (employeeDetails != null) {
            return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK, Constant.COLLEAGUE_DETAILS_FETCH, employeeDetails));

        }
        throw new ResourceNotFoundException("No colleague details found");
    }

    @Override
    public ResponseEntity<ApiResponseDto> getAllEmployeesDetails() {

        List<EmployeeDetails> employeeDetails = employeeDetailsRepoService.toFindAll();
        List<EmployeesDto> employees = new ArrayList<>();

        for (EmployeeDetails employee : employeeDetails) {
            EmployeesDto employeeDto = EmployeesDto.builder()
                    .dob(employee.getDob())
                    .email(employee.getEmail())
                    .empId(employee.getEmpId())
                    .firstName(employee.getFirstName())
                    .lastName(employee.getLastName())
                    .phone(employee.getPhone())
                    .roleName(employee.getRole().getRoleName())
                    .build();
            employees.add(employeeDto);

        }
        return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK, Constant.EMPLOYEE_DETAILS_FETCHED, employees));
    }

    @Override
    public ResponseEntity<ApiResponseDto> getEmployeeIdAndName() {
        List<EmployeeIdNameDto> employeeIdNameDtos=new ArrayList<>();
        List<EmployeeDetails> employeeDetails=employeeDetailsRepoService.toFindAll();
        for(EmployeeDetails employeeDetails1:employeeDetails){
                EmployeeIdNameDto employeeIdNameDto=EmployeeIdNameDto.builder()
                        .empId(employeeDetails1.getEmpId())
                        .firstName(employeeDetails1.getFirstName())
                        .lastName(employeeDetails1.getLastName())
                        .build();
                employeeIdNameDtos.add(employeeIdNameDto);

        }
        return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK,Constant.EMPLOYEE_DETAILS_FETCHED,employeeIdNameDtos));
    }

    public ResponseEntity<ApiResponseDto> getEmployeeById(Long empId) {
        EmployeeDetails employeeDetails = employeeDetailsRepoService.findByEmpId(empId)
                .orElseThrow(() -> new ResourceNotFoundException(Constant.EMPLOYEE_ID_NOT_FOUND + empId));

        EmployeeDto employeeDto = new EmployeeDto(
                employeeDetails.getFirstName(),
                employeeDetails.getLastName(),
                employeeDetails.getEmpId(),
                employeeDetails.getDob(),
                employeeDetails.getRole().getRoleName(),
                employeeDetails.getPhone(),
                employeeDetails.getEmail()
        );

        return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK, Constant.EMPLOYEE_DETAILS_RETRIEVED, employeeDto));
    }

    @Override
    public ResponseEntity<ApiResponseDto> deleteEmployeeById(Long empId) {
        EmployeeDetails employeeDetails = employeeDetailsRepoService.findByEmpId(empId)
                .orElseThrow(() -> new ResourceNotFoundException(Constant.EMPLOYEE_ID_NOT_FOUND + empId));

        employeeDetails.setDeletedAt(new Timestamp(System.currentTimeMillis()));
        employeeDetailsRepoService.saveUserData(employeeDetails);

        return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK, Constant.EMPLOYEE_DELETED, null));
    }

    @Override
    public ResponseEntity<ApiResponseDto> searchEmployees() {
        List<Map<String, Object>> employeesList = employeeDetailsRepoService.getAllEmployees();
        return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK, Constant.EMPLOYEE_DETAILS_RETRIEVED, employeesList));
    }

}
