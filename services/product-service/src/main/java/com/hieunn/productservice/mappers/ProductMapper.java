package com.hieunn.productservice.mappers;
import com.hieunn.commonlib.dtos.products.ProductDto;
import com.hieunn.productservice.entities.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDto.Response toResponse(Product product);
}
