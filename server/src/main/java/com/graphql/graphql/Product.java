package com.graphql.graphql;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public record Product(UUID id, String name, int product_no, UUID manufacturerId) {

    public static UUID ID_1 = UUID.fromString("4cfe25a2-529e-432e-be08-87ee9094774a");
    public static UUID ID_2 = UUID.fromString("4cfe25a2-529e-432e-be08-87ee9094774b");
    public static UUID ID_3 = UUID.fromString("4cfe25a2-529e-432e-be08-87ee9094774c");
    public static UUID ID_4 = UUID.fromString("4cfe25a2-529e-432e-be08-87ee9094774d");

    public static List<Product> products = Arrays.asList(
            new Product(ID_1, "BR 103", 3054, Manufacturer.ID_MAERKLIN),
            new Product(ID_2, "V 200", 3021, Manufacturer.ID_MAERKLIN),
            new Product(ID_3, "V 100", 3072, Manufacturer.ID_MAERKLIN),
            new Product(ID_4, "BR 211", 52321, Manufacturer.ID_PIKO));

    public static Product getProductById(UUID id) {
        return products.stream().filter(p -> p.id().equals(id)).findFirst().orElseThrow();
    }
}
