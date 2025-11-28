package com.hieunn.productservice.controllers;

import com.hieunn.commonlib.dtos.ApiResponse;
import com.hieunn.commonlib.dtos.products.ProductDTO;
import com.hieunn.productservice.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ApiResponse<ProductDTO.Response>> create(
            @RequestBody @Valid ProductDTO.CreateRequest request) {
        ProductDTO.Response response = productService.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(response));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDTO.Response>> update(
            @PathVariable Integer id,
            @RequestBody @Valid ProductDTO.UpdateRequest request) {
        ProductDTO.Response response = productService.update(id, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id) {
        productService.delete(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(ApiResponse.success("Delete successfully"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductDTO.Response>>> getAll() {
        List<ProductDTO.Response> response = productService.getAll();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDTO.Response>> getById(@PathVariable Integer id) {
        ProductDTO.Response response = productService.getById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(response));
    }
}
