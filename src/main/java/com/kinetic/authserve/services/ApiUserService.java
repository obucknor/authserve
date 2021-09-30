package com.kinetic.authserve.services;

import com.kinetic.authserve.models.ApiUser;
import com.kinetic.authserve.models.Role;

import java.util.List;

public interface ApiUserService {

    ApiUser saveUser(ApiUser user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String RoleName);
    ApiUser getUser(String username);
    List<ApiUser> getAllUsers();
    List<Role> getAllRoles();
}
