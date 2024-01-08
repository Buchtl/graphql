package com.graphql.graphql;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
    public static UUID ID_1 = UUID.fromString("4cfe25a2-529e-432e-be08-87ee9094774a");
    public static UUID ID_2 = UUID.fromString("4cfe25a2-529e-432e-be08-87ee9094774b");
    public static UUID ID_3 = UUID.fromString("4cfe25a2-529e-432e-be08-87ee9094774c");
    public static UUID ID_4 = UUID.fromString("4cfe25a2-529e-432e-be08-87ee9094774d");
    private final List<Product> products = new ArrayList<>();

    public ProductService() {
        System.out.println("DataService constructor");



        products.addAll(Arrays.asList(new Product(ID_1, "BR 103", List.of(3054, 1003054), Manufacturer.ID_MAERKLIN),
                new Product(ID_2, "V 200", List.of(3021 , 100321), Manufacturer.ID_MAERKLIN),
                new Product(ID_3, "V 100", List.of(3072 , 100372), Manufacturer.ID_MAERKLIN),
                new Product(ID_4, "BR 211", List.of(52321 , 10052321), Manufacturer.ID_PIKO)));
    }

    public Product getProductById(UUID id) {
        return this.products.stream().filter(p -> p.id().equals(id)).findFirst().orElseThrow();
    }

    public Product createProduct(String name, List<Integer> product_no) {
        UUID id = UUID.randomUUID();
        Product product = new Product(id, name, product_no, Manufacturer.ID_MAERKLIN);
        this.products.add(product);
        return product;
    }

    public void addProductById(UUID id) {
        this.products.add(new Product(id, "", List.of(0), null));
    }
}
