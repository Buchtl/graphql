package com.graphql.graphql.controller;

import com.graphql.graphql.Manufacturer;
import com.graphql.graphql.Product;
import com.graphql.graphql.ProductPublisher;
import com.graphql.graphql.services.ProductService;
import org.springframework.graphql.data.method.annotation.*;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.UUID;

@Controller
public class ProductController {

    final ProductService productService;
    final ProductPublisher productPublisher;

    public ProductController(ProductService productService, ProductPublisher productPublisher) {
        this.productService = productService;
        this.productPublisher = productPublisher;
    }

    @QueryMapping
    public Product helloApollo() {
        return new Product(UUID.randomUUID(), "Hello Apollo from the Backend!", List.of(1, 2, 3), UUID.randomUUID());
    }

    @QueryMapping
    public Product productById(@Argument UUID id) {
        return productService.getProductById(id);
    }

    @MutationMapping
    public Product createProduct(@Argument String name, @Argument List<Integer> product_no) {
        Product created = productService.createProduct(name, product_no);
        productPublisher.emit(created);
        return created;
    }

    @SchemaMapping
    public Manufacturer manufacturer(Product product) {
        return Manufacturer.getManufacturerById(product.manufacturerId());
    }

    @SubscriptionMapping
    public Flux<Product> productAdded() {
        return productPublisher.getPublisher();
    }

}
