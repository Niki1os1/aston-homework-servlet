package com.aston.course;

import com.aston.course.dto.UserDto;
import com.aston.course.model.ERole;
import com.aston.course.model.Role;
import com.aston.course.model.User;
import com.aston.course.repository.RoleRepository;
import com.aston.course.repository.UserRepository;
import com.aston.course.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllUsers() {
        List<User> users = List.of(new User(1L, "username", "email", "password", new HashSet<>()));
        when(userRepository.findAll()).thenReturn(users);

        List<UserDto> userDtos = userService.getAllUsers();

        assertNotNull(userDtos);
        assertEquals(1, userDtos.size());
        assertEquals("username", userDtos.get(0).getUsername());
    }

    @Test
    void testGetUserById() {
        User user = new User(1L, "username", "email", "password", new HashSet<>());
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserDto userDto = userService.getUserById(1L);

        assertNotNull(userDto);
        assertEquals("username", userDto.getUsername());
    }

    @Test
    void testGetRolesByUserId() {
        Set<Role> roles = new HashSet<>();
        User user = new User(1L, "username", "email", "password", roles);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Set<Role> retrievedRoles = userService.getRolesByUserId(1L);

        assertNotNull(retrievedRoles);
        assertEquals(roles, retrievedRoles);
    }

    @Test
    void testCreateUser() {
        User user = new User(1L, "username", "email", "password", new HashSet<>());
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(roleRepository.findByName(ERole.ROLE_STUDENT)).thenReturn(Optional.of(new Role(1L, ERole.ROLE_STUDENT)));

        Long userId = userService.createUser("username", "email", "password");

        assertNotNull(userId);
        assertEquals(1L, userId);
    }

    @Test
    void testDeleteUserById() {
        doNothing().when(userRepository).deleteById(1L);

        Long deletedUserId = userService.deleteUserById(1L);

        assertNotNull(deletedUserId);
        assertEquals(1L, deletedUserId);
    }

    @Test
    void testEditUser() {
        User user = new User(1L, "username", "email", "password", new HashSet<>());
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDto userDto = new UserDto(1L, "newUsername", "newEmail", null, new HashSet<>());
        Long editedUserId = userService.editUser(1L, userDto);

        assertNotNull(editedUserId);
        assertEquals(1L, editedUserId);
        assertEquals("newUsername", user.getUsername());
        assertEquals("newEmail", user.getEmail());
    }

    @Test
    void testEditRoleUserById() {
        User user = new User(1L, "username", "email", "password", new HashSet<>(Set.of(new Role(1L, ERole.ROLE_STUDENT))));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(roleRepository.findByNameIn(anySet())).thenReturn(Set.of(new Role(2L, ERole.ROLE_TEACHER)));

        Long editedUserId = userService.editRoleUserById(1L, new String[]{"ROLE_TEACHER"});

        assertNotNull(editedUserId);
        assertEquals(1L, editedUserId);
        assertTrue(user.getRoles().stream().anyMatch(role -> role.getName().equals(ERole.ROLE_TEACHER)));
    }
}
