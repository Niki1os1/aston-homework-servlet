package com.aston.course.repository;

import com.aston.course.model.ERole;
import com.aston.course.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);

    Set<Role> findByNameIn(Set<ERole> names);
}