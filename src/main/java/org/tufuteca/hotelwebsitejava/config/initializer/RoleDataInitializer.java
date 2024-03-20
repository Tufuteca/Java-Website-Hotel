package org.tufuteca.hotelwebsitejava.config.initializer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.tufuteca.hotelwebsitejava.model.Role;
import org.tufuteca.hotelwebsitejava.repository.RoleRepository;

import java.util.Arrays;

@Component
public class RoleDataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public RoleDataInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Проверяем, есть ли роли в базе данных
        if (roleRepository.count() == 0) {
            // Если нет, создаем роли ROLE_ADMIN и ROLE_USER
            Role adminRole = new Role();
            adminRole.setTitle("ROLE_ADMIN");
            Role userRole = new Role();
            userRole.setTitle("ROLE_USER");
            // Сохраняем их в базе данных
            roleRepository.saveAll(Arrays.asList(adminRole, userRole));
        }
    }
}
