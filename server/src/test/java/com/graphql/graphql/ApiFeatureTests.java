package com.graphql.graphql;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApiFeatureTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;


    private HttpEntity<String> buildEntity(String query) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(query, headers);
    }

    private ResponseEntity<String> postForEntity(String query) {
        String url = "http://localhost:" + port + "/graphql";
        return testRestTemplate.postForEntity(url, buildEntity(query), String.class);
    }

    @Test
    public void testProductByIdQuery() throws JSONException {
        String expectedId = "4cfe25a2-529e-432e-be08-87ee9094774a";
        String query = "{\"query\": \"query details {productById(id: \\\"" + expectedId + "\\\") { id name product_no manufacturer { id name } } }\"}";
        ResponseEntity<String> responseEntity = postForEntity(query);

        JSONObject jsonProduct = new JSONObject(responseEntity.getBody()).getJSONObject("data")
                .getJSONObject("productById");

        assertEquals(expectedId, jsonProduct.getString("id"));
        assertEquals(3054, jsonProduct.getJSONArray("product_no").get(0));
        assertEquals(1003054, jsonProduct.getJSONArray("product_no").get(1));
    }

    @Test
    public void testCreateProductMutation() throws JSONException {
        String expected = "BR666";
        String query = "{\"query\": \"mutation createProduct {createProduct(name: \\\"" + expected + "\\\", product_no: [123456, 789]) { id name product_no manufacturer { id name } } }\"}";
        ResponseEntity<String> responseEntity = postForEntity(query);

        JSONObject product = new JSONObject(responseEntity.getBody()).getJSONObject("data").getJSONObject("createProduct");

        assertEquals(expected, product.getString("name"));
        assertEquals(123456, product.getJSONArray("product_no").get(0));
        assertEquals(789, product.getJSONArray("product_no").get(1));
    }
}

