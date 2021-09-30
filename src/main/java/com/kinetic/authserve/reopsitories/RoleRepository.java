package com.kinetic.authserve.reopsitories;

import com.kinetic.authserve.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);
}
