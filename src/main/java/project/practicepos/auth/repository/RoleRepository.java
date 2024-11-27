package project.practicepos.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.practicepos.auth.entity.RoleEntity;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, String> {
    Optional<RoleEntity> findByName(String name);
    List<RoleEntity> findByNameIn(List<String> roles);
}
