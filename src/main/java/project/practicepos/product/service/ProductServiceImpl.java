package project.practicepos.product.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import project.practicepos.category.model.CategoryEntity;
import project.practicepos.category.repository.CategoryRepository;
import project.practicepos.product.model.ProductEntity;
import project.practicepos.product.model.ProductRequest;
import project.practicepos.product.model.ProductResponse;
import project.practicepos.product.repository.ProductRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

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
        ProductEntity result = this.getEntityById(id);
        if (result == null) {
            return Optional.empty();
        }
        return Optional.of(convertEntityToRes(result));
    }

    @Override
    public Optional<ProductResponse> save(ProductRequest productRequest) {
        ProductEntity result = this.convertReqToEntity(productRequest);
        try {
            this.productRepository.save(result);
            log.info("Saved product success: {}", result);
            return Optional.of(convertEntityToRes(result));
        } catch (Exception e) {
            log.error("Saved product failed error: {}", e.getMessage());
            return Optional.empty();
        }
    }



    @Override
    public Optional<ProductResponse> update(String id, ProductRequest productRequest) {
        ProductEntity result = this.getEntityById(id);
        if (result == null) {
            return Optional.empty();
        }

        convertReqToEntity(productRequest, result);
        try {
            this.productRepository.save(result);
            log.info("Updated product success: {}", result);
            return Optional.of(convertEntityToRes(result));
        } catch (Exception e) {
            log.error("Updated product failed error: {}", e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<ProductResponse> delete(String id) {
        ProductEntity result = this.getEntityById(id);
        if (result == null) {
            return Optional.empty();
        }
        try {
            this.productRepository.delete(result);
            log.info("Deleted product success: {}", result);
            return Optional.of(convertEntityToRes(result));
        } catch (Exception e) {
            log.error("Deleted product failed error: {}", e.getMessage());
            return Optional.empty();
        }
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

    private ProductEntity getEntityById(String id) {
        ProductEntity result = this.productRepository.findById(id).orElse(null);
        if (result == null) {
            log.info("product with id {} not found", id);
        }
        return result;
    }

    private ProductEntity convertReqToEntity(ProductRequest productRequest) {
        CategoryEntity result = categoryRepository.findById(productRequest.getCategoryId()).orElse(null);
        if (result == null) {
            log.info("category with id {} not found", productRequest.getCategoryId());
        }

        ProductEntity productEntity = new ProductEntity();
        BeanUtils.copyProperties(productRequest, productEntity);
        productEntity.setId(UUID.randomUUID().toString());
        productEntity.setCategory(result);
        return productEntity;

    }

    private void convertReqToEntity(ProductRequest request, ProductEntity productEntity) {
        BeanUtils.copyProperties(request, productEntity);
    }
}
