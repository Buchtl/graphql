package com.graphql.graphql;

import java.util.List;
import java.util.UUID;

public record Product(UUID id, String name, List<Integer> product_no, UUID manufacturerId) {

}
