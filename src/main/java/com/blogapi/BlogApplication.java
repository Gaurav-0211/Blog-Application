package com.blogapi;

import com.blogapi.config.AppConstants;
import com.blogapi.entity.Role;
import com.blogapi.repo.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class BlogApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepo roleRepo;
	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(this.passwordEncoder.encode("Abc@123"));
		try {
			if (roleRepo.count() == 0) {
				Role role = new Role();
				role.setId(AppConstants.ADMIN_USER);
				role.setName("ADMIN_USER");

				Role role1 = new Role();
				role1.setId(AppConstants.NORMAL_USER);
				role1.setName("NORMAL_USER");

				List<Role> roles = List.of(role, role1);
				List<Role> result = this.roleRepo.saveAll(roles);
				result.forEach(r -> System.out.println(r.getName()));
			} else {
				System.out.println("Roles already exist. Skipping insertion.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
