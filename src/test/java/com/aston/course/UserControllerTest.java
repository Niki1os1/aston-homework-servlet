package com.aston.course;

import com.aston.course.controller.UserController;
import com.aston.course.dto.UserDto;
import com.aston.course.model.Role;
import com.aston.course.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserRoles() {
        Set<Role> roles = new HashSet<>();
        when(userService.getRolesByUserId(1L)).thenReturn(roles);

        ResponseEntity<Set<Role>> response = userController.getUserRoles("1");

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(roles, response.getBody());
    }

    @Test
    void testGetUserById() {
        UserDto userDto = new UserDto(1L, "username", "email", "password", new HashSet<>());
        when(userService.getUserById(1L)).thenReturn(userDto);

        ResponseEntity<UserDto> response = userController.getUserById("1");

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(userDto, response.getBody());
    }

    @Test
    void testGetAllUsers() {
        List<UserDto> users = List.of(new UserDto(1L, "username", "email", "password", new HashSet<>()));
        when(userService.getAllUsers()).thenReturn(users);

        ResponseEntity<List<UserDto>> response = userController.getAllUsers();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(users, response.getBody());
    }

    @Test
    void testCreateUser() {
        when(userService.createUser("username", "email", "password")).thenReturn(1L);

        ResponseEntity<Long> response = userController.createUser("username", "email", "password");

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1L, response.getBody());
    }

    @Test
    void testEditUser() {
        UserDto userDto = new UserDto();
        when(userService.editUser(1L, userDto)).thenReturn(1L);

        ResponseEntity<Long> response = userController.editUser("1", userDto);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1L, response.getBody());
    }

    @Test
    void testEditRoleUserById() {
        String[] roles = {"ROLE_STUDENT"};
        when(userService.editRoleUserById(1L, roles)).thenReturn(1L);

        ResponseEntity<Long> response = userController.editRoleUserById("1", roles);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1L, response.getBody());
    }

    @Test
    void testDeleteUserById() {
        when(userService.deleteUserById(1L)).thenReturn(1L);

        ResponseEntity<Long> response = userController.deleteUserById("1");

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1L, response.getBody());
    }
}
