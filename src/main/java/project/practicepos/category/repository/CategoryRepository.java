package project.practicepos.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import project.practicepos.category.entity.CategoryEntity;

@RequestMapping
public interface CategoryRepository extends JpaRepository<CategoryEntity, String> {
}
