package com.kinetic.authserve;

import com.kinetic.authserve.models.ApiUser;
import com.kinetic.authserve.models.Role;
import com.kinetic.authserve.services.ApiUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class AuthserveApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthserveApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner run(ApiUserService userService){
		return args -> {
			userService.saveRole(new Role(null, "ROLE_USER"));
			userService.saveRole(new Role(null, "ROLE_MANAGER"));
			userService.saveRole(new Role(null, "ROLE_ADMIN"));
			userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));

			userService.saveUser(new ApiUser(null, "James Brown", "james@mail.com", "james1234", new ArrayList<>()));
			userService.saveUser(new ApiUser(null, "John Travolta", "john@mail.com", "john1234", new ArrayList<>()));
			userService.saveUser(new ApiUser(null, "Will Smith", "will@mail.com", "will1234", new ArrayList<>()));
			userService.saveUser(new ApiUser(null, "Barak Obama", "barak@mail.com", "barak1234", new ArrayList<>()));

			userService.addRoleToUser("james@mail.com", "ROLE_USER");
			userService.addRoleToUser("james@mail.com", "ROLE_MANAGER");
			userService.addRoleToUser("john@mail.com", "ROLE_MANAGER");
			userService.addRoleToUser("will@mail.com", "ROLE_ADMIN");
			userService.addRoleToUser("barak@mail.com", "ROLE_SUPER_ADMIN");
			userService.addRoleToUser("barak@mail.com", "ROLE_ADMIN");
			userService.addRoleToUser("barak@mail.com", "ROLE_USER");
		};
	}

}
