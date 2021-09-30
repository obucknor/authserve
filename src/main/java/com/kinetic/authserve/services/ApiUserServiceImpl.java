package com.kinetic.authserve.services;

import com.kinetic.authserve.models.ApiUser;
import com.kinetic.authserve.models.Role;
import com.kinetic.authserve.reopsitories.ApiUserRepository;
import com.kinetic.authserve.reopsitories.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ApiUserServiceImpl implements ApiUserService, UserDetailsService {

    private final ApiUserRepository apiUserRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        ApiUser apiUser = apiUserRepository.findByUsername(username);
        if(apiUser == null) {
            log.error("User {} not found in the database!", username);
            throw new UsernameNotFoundException("User '" + username +"' not found in the database!");
        } else {
            log.info("user found in the database: {}", username);
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        apiUser.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new User(apiUser.getUsername(), apiUser.getPassword(), authorities);
    }

    @Override
    public ApiUser saveUser(ApiUser user)
    {
        log.info("Saving new user {} to the database.", user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return apiUserRepository.save(user);
    }

    @Override
    public Role saveRole(Role role)
    {
        log.info("Saving new role {} to the database.", role.getName());
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName)
    {
        log.info("Adding role {} to ApiUser {}", roleName, username );
        ApiUser apiUser = apiUserRepository.findByUsername(username);
        Role role = roleRepository.findByName(roleName);
        apiUser.getRoles().add(role);
    }

    @Override
    public ApiUser getUser(String username)
    {
        log.info("fetching user {}", username);
        return apiUserRepository.findByUsername(username);
    }

    @Override
    public List<ApiUser> getAllUsers()
    {
        log.info("Fetching all users..");
        return apiUserRepository.findAll();
    }

    @Override
    public List<Role> getAllRoles() {
        log.info("Fetching All Roles..");
        return roleRepository.findAll();
    }


}
