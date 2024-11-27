package project.practicepos.category.service;

import project.practicepos.category.request.CategoryRequest;
import project.practicepos.category.response.CategoryResponse;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<CategoryResponse> get();
    Optional<CategoryResponse> getById(String id);
    Optional<CategoryResponse> create(CategoryRequest categoryRequest);
    Optional<CategoryResponse> update(String id, CategoryRequest categoryRequest);
    Optional<CategoryResponse> delete(String id);
}