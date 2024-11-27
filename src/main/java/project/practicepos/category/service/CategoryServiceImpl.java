package project.practicepos.category.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import project.practicepos.category.entity.CategoryEntity;
import project.practicepos.category.repository.CategoryRepository;
import project.practicepos.category.request.CategoryRequest;
import project.practicepos.category.response.CategoryResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
        return Optional.empty();
    }

    @Override
    public Optional<CategoryResponse> create(CategoryRequest categoryRequest) {
        return Optional.empty();
    }

    @Override
    public Optional<CategoryResponse> update(String id, CategoryRequest categoryRequest) {
        return Optional.empty();
    }

    @Override
    public Optional<CategoryResponse> delete(String id) {
        return Optional.empty();
    }

    private CategoryResponse convertEntityToRes(CategoryEntity categoryEntity) {
        CategoryResponse categoryResponse = new CategoryResponse();
        BeanUtils.copyProperties(categoryEntity, categoryResponse);
        return categoryResponse;
    }
}
