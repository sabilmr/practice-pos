package project.practicepos.category.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import project.practicepos.category.model.CategoryEntity;
import project.practicepos.category.repository.CategoryRepository;
import project.practicepos.category.model.CategoryRequest;
import project.practicepos.category.model.CategoryResponse;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryResponse> get() {
        List<CategoryEntity> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            return Collections.emptyList();
        }
        return categories.stream()
                .map(this::convertEntityToRes)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CategoryResponse> getById(String id) {
        CategoryEntity categoryEntity = this.getEntityById(id);
        if (categoryEntity == null) {
            return Optional.empty();
        }
        return Optional.of(convertEntityToRes(categoryEntity));
    }

    @Override
    public Optional<CategoryResponse> create(CategoryRequest categoryRequest) {
        CategoryEntity categoryEntity = this.convertReqToEntity(categoryRequest);

        categoryEntity.setId(UUID.randomUUID().toString());
        try {
            this.categoryRepository.save(categoryEntity);
            log.info("Created category success: {}", categoryEntity);
            return Optional.of(convertEntityToRes(categoryEntity));
        } catch (Exception e) {
            log.error("Created category failed error: {}", e.getMessage());
            return Optional.empty();
        }

    }

    @Override
    public Optional<CategoryResponse> update(String id, CategoryRequest categoryRequest) {
        CategoryEntity categoryEntity = this.getEntityById(id);
        if (categoryEntity == null) {
            return Optional.empty();
        }

        this.convertReqToEntity(categoryRequest, categoryEntity);
        categoryEntity.setId(id);
        try {
            this.categoryRepository.save(categoryEntity);
            log.info("Updated category success: {}", categoryEntity);
            return Optional.of(convertEntityToRes(categoryEntity));
        } catch (Exception e) {
            log.error("Updated category failed error: {}", e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<CategoryResponse> delete(String id) {
        CategoryEntity categoryEntity = this.getEntityById(id);
        if (categoryEntity == null) {
            return Optional.empty();
        }
        try {
            this.categoryRepository.delete(categoryEntity);
            log.info("Deleted category success: {}", categoryEntity);
            return Optional.of(convertEntityToRes(categoryEntity));
        }
        catch (Exception e) {
            log.error("Deleted category failed error: {}", e.getMessage());
            return Optional.empty();
        }
    }

    private CategoryResponse convertEntityToRes(CategoryEntity categoryEntity) {
        CategoryResponse categoryResponse = new CategoryResponse();
        BeanUtils.copyProperties(categoryEntity, categoryResponse);
        return categoryResponse;
    }

    private CategoryEntity getEntityById(String id) {
        CategoryEntity categoryEntity = categoryRepository.findById(id).orElse(null);
        if (categoryEntity == null) {
            return null;
        }
        return categoryEntity;
    }

    private CategoryEntity convertReqToEntity(CategoryRequest categoryRequest) {
        CategoryEntity categoryEntity = new CategoryEntity();
        BeanUtils.copyProperties(categoryRequest, categoryEntity);
        return categoryEntity;
    }

    private void convertReqToEntity(CategoryRequest categoryRequest, CategoryEntity entity) {
        BeanUtils.copyProperties(categoryRequest, entity);
    }
}
