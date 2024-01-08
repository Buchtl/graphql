package com.graphql.graphql;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class ProductController {

    final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @QueryMapping
    public Product productById(@Argument UUID id) {
        return productService.getProductById(id);
    }

    @MutationMapping
    public Product createProduct(@Argument String name, @Argument List<Integer> product_no) {
        return productService.createProduct(name, product_no);
    }

    @SchemaMapping
    public Manufacturer manufacturer(Product product) {
        return Manufacturer.getManufacturerById(product.manufacturerId());
    }

}
