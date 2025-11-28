package com.hieunn.productservice.services;

import com.hieunn.commonlib.dtos.products.ProductDto;

import java.util.List;

public interface ProductService {
    ProductDto.Response create(ProductDto.CreateRequest request);

    ProductDto.Response update(Integer id, ProductDto.UpdateRequest request);

    void delete(Integer id);

    List<ProductDto.Response> getAll();

    ProductDto.Response getById(Integer id);
}
