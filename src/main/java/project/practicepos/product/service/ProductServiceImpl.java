package project.practicepos.product.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import project.practicepos.product.model.ProductEntity;
import project.practicepos.product.model.ProductRequest;
import project.practicepos.product.model.ProductResponse;
import project.practicepos.product.repository.ProductRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<ProductResponse> get() {
        List<ProductEntity> results = productRepository.findAll();
        if (results.isEmpty()) {
            return Collections.emptyList();
        }

        return results.stream()
                .map(this::convertEntityToRes)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ProductResponse> getById(String id) {
        return Optional.empty();
    }

    @Override
    public Optional<ProductResponse> save(ProductRequest productRequest) {
        return Optional.empty();
    }

    @Override
    public Optional<ProductResponse> update(String id, ProductRequest productRequest) {
        return Optional.empty();
    }

    @Override
    public Optional<ProductResponse> delete(String id) {
        return Optional.empty();
    }

    private ProductResponse convertEntityToRes(ProductEntity productEntity) {
        ProductResponse productResponse = new ProductResponse();
        BeanUtils.copyProperties(productEntity, productResponse);

        if (productEntity.getCategory() != null) {
            productResponse.setCategoryId(productEntity.getCategory().getId());
            productResponse.setCategoryName(productEntity.getCategory().getName());
        }

        return productResponse;
    }
}
