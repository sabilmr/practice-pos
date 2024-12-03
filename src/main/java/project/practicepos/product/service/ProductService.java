package project.practicepos.product.service;

import project.practicepos.product.model.ProductRequest;
import project.practicepos.product.model.ProductResponse;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<ProductResponse> get();
    Optional<ProductResponse> getById(String id);
    Optional<ProductResponse> save(ProductRequest productRequest);
    Optional<ProductResponse> update(String id, ProductRequest productRequest);
    Optional<ProductResponse> delete(String id);
}
