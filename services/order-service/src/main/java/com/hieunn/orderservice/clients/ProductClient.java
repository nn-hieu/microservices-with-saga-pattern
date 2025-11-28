package com.hieunn.orderservice.clients;

import com.hieunn.commonlib.dtos.ApiResponse;
import com.hieunn.commonlib.dtos.products.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service")
public interface ProductClient {
    @GetMapping("/api/products/{id}")
    ApiResponse<ProductDto.Response> getById(@PathVariable("id") Integer id);
}
