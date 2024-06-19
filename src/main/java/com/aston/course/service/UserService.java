package com.aston.course.service;

import com.aston.course.dto.UserDto;
import com.aston.course.model.ERole;
import com.aston.course.model.Role;
import com.aston.course.model.User;
import com.aston.course.repository.RoleRepository;
import com.aston.course.repository.UserRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::mapUserToDto).collect(Collectors.toList());
    }

    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + id));
        return mapUserToDto(user);
    }

    public Set<Role> getRolesByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + userId));
        return user.getRoles();
    }

    @Transactional
    public Long createUser(String username, String email, String password) {
        User savedUser = new User(username, email, password);

        Set<Role> role = new HashSet<>();

        Role userRole = roleRepository.findByName(ERole.ROLE_STUDENT)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        role.add(userRole);

        savedUser.setRoles(role);

        return userRepository.save(savedUser).getId();
    }

    @Transactional
    public Long deleteUserById(Long userId) {
        userRepository.deleteById(userId);
        return userId;
    }

    @Transactional
    public Long editUser(Long userId, UserDto userDto) {
        User savedUser = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + userId));

        if (StringUtils.isNotBlank(userDto.getUsername())) {
            savedUser.setUsername(userDto.getUsername());
        }

        if (StringUtils.isNotBlank(userDto.getEmail())) {
            savedUser.setEmail(userDto.getEmail());
        }

        userRepository.save(savedUser);
        return savedUser.getId();
    }

    @Transactional
    public Long editRoleUserById(Long userId, String[] roles) {
        User savedUser = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + userId));

        savedUser.getRoles().removeIf(role -> !role.getName().equals(ERole.ROLE_STUDENT));

        Set<ERole> roleEnums = new HashSet<>();
        for (String roleName : roles) {
            try {
                roleEnums.add(ERole.valueOf(roleName));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid role name: " + roleName);
            }
        }

        Set<Role> rolesToAdd = roleRepository.findByNameIn(roleEnums);

        if (rolesToAdd.size() != roleEnums.size()) {
            throw new NoSuchElementException("One or more roles not found in the database");
        }

        savedUser.getRoles().addAll(rolesToAdd);

        userRepository.save(savedUser);

        return savedUser.getId();
    }

    private UserDto mapUserToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        Set<Role> roles = user.getRoles();
        Set<String> roleNames = new HashSet<>();

        for (Role role : roles) {
            roleNames.add(role.getName().name());
        }

        userDto.setRoles(roleNames);

        return userDto;
    }
}