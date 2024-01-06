package com.graphql.graphql;

import java.util.UUID;

public record Product(UUID id, String name, int product_no, UUID manufacturerId) {

}
