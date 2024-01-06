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
        String expected = "4cfe25a2-529e-432e-be08-87ee9094774a";
        String query = "{\"query\": \"query details {productById(id: \\\"" + expected + "\\\") { id name product_no manufacturer { id name } } }\"}";
        ResponseEntity<String> responseEntity = postForEntity(query);

        JSONObject jsonResponse = new JSONObject(responseEntity.getBody());

        String actual = jsonResponse.getJSONObject("data").getJSONObject("productById").getString("id");

        assertEquals(expected, actual);
    }

    @Test
    public void testCreateProductMutation() throws JSONException {
        String expected = "BR666";
        String query = "{\"query\": \"mutation createProduct {createProduct(name: \\\"" + expected + "\\\", product_no: \\\"123456\\\") { id name product_no manufacturer { id name } } }\"}";
        ResponseEntity<String> responseEntity = postForEntity(query);

        JSONObject jsonResponse = new JSONObject(responseEntity.getBody());

        String actual = jsonResponse.getJSONObject("data").getJSONObject("createProduct").getString("name");

        assertEquals(expected, actual);
    }
}

