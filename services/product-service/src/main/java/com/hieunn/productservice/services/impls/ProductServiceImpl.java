package com.hieunn.productservice.services.impls;

import com.hieunn.commonlib.dtos.products.ProductDTO;
import com.hieunn.commonlib.exceptions.NotFoundException;
import com.hieunn.productservice.entities.Product;
import com.hieunn.productservice.mappers.ProductMapper;
import com.hieunn.productservice.repositories.ProductRepository;
import com.hieunn.productservice.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final String productsCacheName = "products";

    @Override
    @Transactional
    public ProductDTO.Response create(ProductDTO.CreateRequest request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setIsActive(request.getIsActive());

        productRepository.save(product);

        return productMapper.toResponse(product);
    }

    @Override
    @Transactional
    @CacheEvict(value = productsCacheName, key = "#id")
    public ProductDTO.Response update(Integer id, ProductDTO.UpdateRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Product not found with id: {}", id);

                    return new NotFoundException("Product not found with id: " + id);
                });

        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setIsActive(request.getIsActive());

        productRepository.save(product);

        return productMapper.toResponse(product);
    }

    @Override
    @Transactional
    @CacheEvict(value = productsCacheName, key = "#id")
    public void delete(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Product not found with id: {}", id);

                    return new NotFoundException("Product not found with id: " + id);
                });

        productRepository.delete(product);
    }

    @Override
    public List<ProductDTO.Response> getAll() {
        return productRepository.findAll(Sort.by("id"))
                .stream()
                .map(productMapper::toResponse)
                .toList();
    }

    @Override
    @Cacheable(value = productsCacheName, key = "#id")
    public ProductDTO.Response getById(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Product not found with id: {}", id);

                    return new NotFoundException("Product not found with id: " + id);
                });

        return productMapper.toResponse(product);
    }
}
