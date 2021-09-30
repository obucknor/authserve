package com.kinetic.authserve.reopsitories;

import com.kinetic.authserve.models.ApiUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiUserRepository extends JpaRepository<ApiUser, Long> {

    ApiUser findByUsername(String username);
}
