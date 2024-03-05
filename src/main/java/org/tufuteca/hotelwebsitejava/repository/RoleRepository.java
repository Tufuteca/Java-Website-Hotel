package org.tufuteca.hotelwebsitejava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tufuteca.hotelwebsitejava.model.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByUserRole(String role);
}