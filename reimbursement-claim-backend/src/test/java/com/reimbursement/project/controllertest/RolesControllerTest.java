package com.reimbursement.project.controllertest;

import com.reimbursement.project.constant.Constant;
import com.reimbursement.project.controller.RolesController;
import com.reimbursement.project.dto.ApiResponseDto;
import com.reimbursement.project.dto.RolesDto;
import com.reimbursement.project.entity.Enum.Role;
import com.reimbursement.project.exception.AlreadyExistException;
import com.reimbursement.project.service.RolesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class RolesControllerTest {
    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }
    @Mock
    RolesService rolesService;
    @InjectMocks
    RolesController rolesController;
    @Test
    void testPostRoles(){
        RolesDto roles=new RolesDto();
        when(rolesController.postRoles(roles)).thenThrow(new AlreadyExistException(Constant.ROLES_ALREADY_EXIST + roles.getRoleName()));
        assertThrows(AlreadyExistException.class,()->rolesController.postRoles(roles));
    }
    @Test
    void testGetRoles(){
        Role role=Role.EMPLOYEE;
        when(rolesController.getRoles()).thenReturn(ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK, Constant.ROLES_FETCHED,role)));
        assertEquals(ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK, Constant.ROLES_FETCHED, role)),rolesController.getRoles());
    }
}
