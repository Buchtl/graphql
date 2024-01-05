package com.graphql.graphql;

import java.util.List;
import java.util.UUID;

public record Manufacturer(UUID id, String name) {
    public static UUID ID_MAERKLIN = UUID.fromString("4cfe25a2-529e-432e-be08-87ee9094775b");
    public static UUID ID_PIKO = UUID.fromString("4cfe25a2-529e-432e-be08-87ee9094775c");

    public static List<Manufacturer> manufacturers = List.of(
            new Manufacturer(ID_MAERKLIN, "Märklin"),
            new Manufacturer(ID_PIKO, "Piko"));

    public static Manufacturer getManufacturerById(UUID id) {
        return manufacturers.stream().filter(m -> m.id().equals(id)).findFirst().orElseThrow();
    }
}
