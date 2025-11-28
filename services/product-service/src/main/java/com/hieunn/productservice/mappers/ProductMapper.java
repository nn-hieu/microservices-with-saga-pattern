package com.hieunn.productservice.mappers;
import com.hieunn.commonlib.dtos.products.ProductDTO;
import com.hieunn.productservice.entities.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDTO.Response toResponse(Product product);
}
