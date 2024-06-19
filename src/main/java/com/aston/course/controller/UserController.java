package com.aston.course.controller;

import com.aston.course.dto.UserDto;
import com.aston.course.model.Role;
import com.aston.course.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@RestController
@RequestMapping(value = "/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}/role")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Set<Role>> getUserRoles(@PathVariable String userId) {
        try {
            Long id = Long.valueOf(userId);
            Set<Role> roles = userService.getRolesByUserId(id);
            return ResponseEntity.ok(roles);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserDto> getUserById(@PathVariable String userId) {
        try {
            Long id = Long.valueOf(userId);
            UserDto userDto = userService.getUserById(id);
            return ResponseEntity.ok(userDto);
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<UserDto>> getAllUsers() {
        try {
            List<UserDto> users = userService.getAllUsers();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Long> createUser(@RequestParam("username") String username,
                                           @RequestParam("email") String email,
                                           @RequestParam("password") String password) {
        try {
            var createdUser = userService.createUser(username, email, password);
            return ResponseEntity.ok(createdUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Long> editUser(@PathVariable String userId, @RequestBody UserDto userDto) {
        try {
            Long id = Long.valueOf(userId);
            Long updatedUserId = userService.editUser(id, userDto);
            return ResponseEntity.ok(updatedUserId);
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/{userId}/roles")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Long> editRoleUserById(@PathVariable String userId, @RequestBody String[] roles) {
        try {
            Long id = Long.valueOf(userId);
            Long updatedUserId = userService.editRoleUserById(id, roles);
            return ResponseEntity.ok(updatedUserId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Long> deleteUserById(@PathVariable String userId) {
        try {
            Long id = Long.valueOf(userId);
            Long deleteUserById = userService.deleteUserById(id);
            return ResponseEntity.ok(deleteUserById);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
