package com.reimbursement.project.servicetest;

import com.reimbursement.project.constant.Constant;
import com.reimbursement.project.dto.ApiResponseDto;
import com.reimbursement.project.dto.RolesDto;
import com.reimbursement.project.entity.Enum.Role;
import com.reimbursement.project.entity.Roles;
import com.reimbursement.project.exception.AlreadyExistException;
import com.reimbursement.project.repository.service.RolesRepoService;
import com.reimbursement.project.service.impl.RolesServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class RolesServiceTest {
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @InjectMocks
    RolesServiceImpl rolesService;
    @Mock
    RolesRepoService rolesRepoService;
    @Mock
    ModelMapper modelMapper;

    @Test
    void testPostRolesAlreadyPresent() {
        RolesDto rolesDto = new RolesDto("ADMIN");
        when(rolesRepoService.isRoleExist(rolesDto.getRoleName())).thenReturn(true);
        assertThrows(AlreadyExistException.class, () -> rolesService.postRoles(rolesDto));
    }

    @Test
    void testPostRoles() {
        RolesDto rolesDto = new RolesDto("ADMIN");
        when(rolesRepoService.isRoleExist(rolesDto.getRoleName())).thenReturn(false);
        Roles roles = new Roles();
        when(modelMapper.map(roles, Roles.class)).thenReturn(roles);
        when(rolesRepoService.toSave(roles)).thenReturn(roles);
        ResponseEntity<ApiResponseDto> expected = ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK, Constant.ROLES_ADDED, rolesDto));
        ResponseEntity<ApiResponseDto> actual = rolesService.postRoles(rolesDto);
        assertEquals(expected, actual);
    }

    @Test
    void testGetRoles() {
        ResponseEntity<ApiResponseDto> expected = ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK, Constant.ROLES_FETCHED, Role.values()));
        ResponseEntity<ApiResponseDto> actual = rolesService.getRoles();
        assertEquals(expected.getStatusCode(), actual.getStatusCode());
        assertEquals(expected.getBody().getMessage(), actual.getBody().getMessage());
    }
}
