package com.ahmaddimas.simpleapi.controller;

import com.ahmaddimas.simpleapi.model.Grade;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GradeControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    int serverPort;

    private String getApiUrl() {
        return "http://localhost:" + serverPort + "/api/v1/grade";
    }

    @Test
    @Order(1)
    void testAddNewGradeShouldSuccess() {
        Grade grade = new Grade("GradeTest");
        ResponseEntity<String> response = testRestTemplate.postForEntity(getApiUrl(), grade, String.class);

        System.out.println(response.getBody());
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
    }

    @Test
    @Order(2)
    void testAddNewGradeShouldFailed() {
        Grade grade = new Grade();
        ResponseEntity<String> response = testRestTemplate.postForEntity(getApiUrl(), grade, String.class);

        System.out.println(response.getBody());
        assertTrue(response.getStatusCode().is5xxServerError());
        assertNotNull(response.getBody());
    }

    @Test
    @Order(3)
    void testGetListShouldSuccess() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = testRestTemplate.exchange(getApiUrl(), HttpMethod.GET, requestEntity, String.class);

        System.out.println(response.getBody());
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
    }

    @Test
    @Order(4)
    void testShowShouldSuccess() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = testRestTemplate.exchange(getApiUrl() + "/1", HttpMethod.GET, requestEntity, String.class);

        System.out.println(response.getBody());
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
    }

    @Test
    @Order(5)
    void testShowShouldFailed() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = testRestTemplate.exchange(getApiUrl() + "/0", HttpMethod.GET, requestEntity, String.class);

        System.out.println(response.getBody());
        assertTrue(response.getStatusCode().is5xxServerError());
        assertNotNull(response.getBody());
    }

    @Test
    @Order(6)
    void testUpdateGradeShouldSuccess() {
        List<Map<String, Object>> response = new ArrayList<>();
        response = testRestTemplate.getForObject(getApiUrl() + "?name=GradeTest", response.getClass());
        Map<String, Object> map = response.get(response.size() - 1);

        Grade grade = new Grade(String.valueOf(map.get("name")));
        grade.setId(Integer.parseInt(map.get("id").toString()));
        System.out.println(grade);

        grade.setName("GradeTest Updated");
        testRestTemplate.put(getApiUrl() + "/" + grade.getId(), grade, String.class);
        response = testRestTemplate.getForObject(getApiUrl() + "?name=GradeTest Updated", response.getClass());

        System.out.println(response);
        assertNotNull(response);
        assertNotEquals(response.size(), 0);
    }

    @Test
    @Order(7)
    void testUpdateGradeShouldFailed() {
        Grade grade = new Grade();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Grade> requestEntity = new HttpEntity<>(grade, headers);
        ResponseEntity<String> response = testRestTemplate.exchange(getApiUrl() + "/1", HttpMethod.PUT, requestEntity, String.class);

        System.out.println(response);
        assertTrue(response.getStatusCode().is5xxServerError());
        assertNotNull(response.getBody());
    }

    @Test
    @Order(8)
    void testDeleteGradeShouldSuccess() {
        List<Map<String, Object>> response = new ArrayList<>();
        response = testRestTemplate.getForObject(getApiUrl() + "?name=GradeTest Updated", response.getClass());

        assertNotNull(response);
        assertNotEquals(response.size(), 0);

        Map<String, Object> map = response.get(response.size() - 1);
        System.out.println(map);

        testRestTemplate.delete(getApiUrl() + "/" + map.get("id"));
        response = testRestTemplate.getForObject(getApiUrl() + "?name=GradeTest Updated", response.getClass());

        System.out.println(response);
        assertNotNull(response);
        assertEquals(response.size(), 0);
    }

    @Test
    @Order(9)
    void testDeleteGradeShouldFailed() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Grade> requestEntity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = testRestTemplate.exchange(getApiUrl() + "/0", HttpMethod.DELETE, requestEntity, String.class);

        System.out.println(response);
        assertTrue(response.getStatusCode().is5xxServerError());
        assertNotNull(response.getBody());
    }
}