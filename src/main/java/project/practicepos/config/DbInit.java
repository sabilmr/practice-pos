package project.practicepos.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import project.practicepos.auth.model.RoleEntity;
import project.practicepos.auth.model.UserEntity;
import project.practicepos.auth.repository.RoleRepository;
import project.practicepos.auth.repository.UserRepository;
import project.practicepos.category.model.CategoryEntity;
import project.practicepos.category.repository.CategoryRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DbInit implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public void run(String... args) throws Exception {
        initRole();
        initUser();
        initCategory();
    }

    private void initCategory() {
        if (!categoryRepository.findAll().isEmpty()) {
            return;
        }

        List<CategoryEntity> categories = Arrays.asList(
                new CategoryEntity("Fashion", "Pakaian, Sepatu, Aksesoris"),
                new CategoryEntity("Elektronik", "Laptop, Smartphone, TV, Peralatan Rumah Tangga"),
                new CategoryEntity("Kesehatan", "Obat-obatan, Alat Medis, Suplemen"),
                new CategoryEntity("Makanan", "Makanan Ringan, Minuman, Makanan Organik"),
                new CategoryEntity("Hobi", "Mainan, Alat Musik")
        );

        try {
            categoryRepository.saveAll(categories);
            log.info("Save category success");
        } catch (Exception e) {
            log.error("Save category failed error : {}", e.getMessage());
        }
    }

    public void initRole() {
        if (roleRepository.count() > 0) {
            log.info("Role already exists");
            return;
        }
        try {
            var roleUser = Arrays.asList(
                    new RoleEntity("ROLE_USER"),
                    new RoleEntity("ROLE_ADMIN")
            );
            roleRepository.saveAllAndFlush(roleUser);
            log.info("Role already exists");
        }catch (Exception e) {
            log.error("Save role error : {}", e.getMessage());
        }
    }

    public void initUser() {
        if (userRepository.count() > 0) {
            log.info("User already exists");
            return;
        }
        List<UserEntity> users = new ArrayList<>();
        RoleEntity roleUser = roleRepository.findByName("ROLE_USER").orElse(null);
        if (roleUser != null) {
            users.add(new UserEntity("user@gmail.com", passwordEncoder.encode("P@ssW0rd32!"), Arrays.asList(roleUser)));
            users.add(new UserEntity("Sabilla Muhammad Rayhan", passwordEncoder.encode("P@ssW0rd32!"), Arrays.asList(roleUser)));
        }
        RoleEntity roleUserAdmin = roleRepository.findByName("ROLE_ADMIN").orElse(null);
        if (roleUserAdmin != null) {
            users.add(new UserEntity("admin@gmail.com", passwordEncoder.encode("P@ssW0rd32!"), Arrays.asList(roleUserAdmin)));
            users.add(new UserEntity("Karina Dwi Lestari", passwordEncoder.encode("P@ssW0rd32!"), Arrays.asList(roleUserAdmin)));
        }
        try {
            userRepository.saveAllAndFlush(users);
            log.info("user already exists");
        } catch (Exception e) {
            log.error("Save user error : {}", e.getMessage());
        }
    }
}
