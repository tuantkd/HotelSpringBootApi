package com.hotelApp.HotelBooking.configs;

import com.hotelApp.HotelBooking.entity.*;
import com.hotelApp.HotelBooking.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private RolePermissionRepository rolePermissionRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public void run(String... args) {
        // Check if data already exists
        if (userRepository.count() > 0 || roleRepository.count() > 0 || permissionRepository.count() > 0) {
            System.out.println("Data already exists. Skipping initialization.");
            return;
        }

        // Create Permissions
        Permission viewDashboard = new Permission();
        viewDashboard.setName("VIEW_DASHBOARD");
        viewDashboard.setDescription("View Dashboard");
        permissionRepository.save(viewDashboard);

        // Create Roles
        Role adminRole = new Role();
        adminRole.setName("Admin");
        adminRole.setDescription("Role Admin");
        roleRepository.save(adminRole);

        // Associate Permissions with Roles
        RolePermission adminViewDashboard = new RolePermission();
        adminViewDashboard.setRole(adminRole);
        adminViewDashboard.setPermission(viewDashboard);
        rolePermissionRepository.save(adminViewDashboard);

        // Create Users
        User adminUser = new User();
        adminUser.setName("Admin");
        adminUser.setEmail("admin@gmail.com");
        adminUser.setPassword(new BCryptPasswordEncoder().encode("admin"));
        userRepository.save(adminUser);

        // Associate Users with Roles
        UserRole adminUserRole = new UserRole();
        adminUserRole.setUser(adminUser);
        adminUserRole.setRole(adminRole);
        userRoleRepository.save(adminUserRole);

        System.out.println("Initial data added to the database.");
    }
}
