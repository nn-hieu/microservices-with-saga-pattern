package com.hieunn.productservice.services;

import com.hieunn.commonlib.dtos.products.ProductDTO;

import java.util.List;

public interface ProductService {
    ProductDTO.Response create(ProductDTO.CreateRequest request);

    ProductDTO.Response update(Integer id, ProductDTO.UpdateRequest request);

    void delete(Integer id);

    List<ProductDTO.Response> getAll();

    ProductDTO.Response getById(Integer id);
}
