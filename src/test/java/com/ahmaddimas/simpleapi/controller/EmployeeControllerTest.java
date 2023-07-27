package com.ahmaddimas.simpleapi.controller;

import com.ahmaddimas.simpleapi.model.Employee;
import com.ahmaddimas.simpleapi.model.Grade;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EmployeeControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    int serverPort;

    private String getApiUrl() {
        return "http://localhost:" + serverPort + "/api/v1/employee";
    }

    @Test
    @Order(1)
    void testAddNewEmployeeShouldSuccess() {
        Employee employee = new Employee("EmployeeTest", 7563000L, 1);
        System.out.println(employee);
        ResponseEntity<String> response = testRestTemplate.postForEntity(getApiUrl(), employee, String.class);

        System.out.println(response.getBody());
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
    }

    @Test
    @Order(2)
    void testAddNewEmployeeShouldFailed() {
        Employee employee = new Employee();
        ResponseEntity<String> response = testRestTemplate.postForEntity(getApiUrl(), employee, String.class);

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
    void testUpdateEmployeeShouldSuccess() {
        List<Map<String, Object>> response = new ArrayList<>();
        response = testRestTemplate.getForObject(getApiUrl() + "?name=EmployeeTest", response.getClass());

        Map<String, Object> map = response.get(response.size() - 1);
        Map<String, Object> gradeMap = (Map<String, Object>) map.get("grade");

        Employee employee = new Employee();
        employee.setId(Long.parseLong(map.get("id").toString()));
        employee.setName(map.get("name").toString());
        employee.setSalary(Long.parseLong(map.get("salary").toString()));
        employee.setGradeId(Integer.parseInt(gradeMap.get("id").toString()));
        System.out.println(employee);

        employee.setName("EmployeeTest Updated");
        testRestTemplate.put(getApiUrl() + "/" + employee.getId(), employee, String.class);
        response = testRestTemplate.getForObject(getApiUrl() + "?name=EmployeeTest Updated", response.getClass());

        System.out.println(response);
        assertNotNull(response);
        assertNotEquals(response.size(), 0);
    }

    @Test
    @Order(7)
    void testUpdateEmployeeShouldFailed() {
        Employee employee = new Employee();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Employee> requestEntity = new HttpEntity<>(employee, headers);
        ResponseEntity<String> response = testRestTemplate.exchange(getApiUrl() + "/1", HttpMethod.PUT, requestEntity, String.class);

        System.out.println(response);
        assertTrue(response.getStatusCode().is5xxServerError());
        assertNotNull(response.getBody());
    }

    @Test
    @Order(8)
    void testDeleteEmployeeShouldSuccess() {
        List<Map<String, Object>> response = new ArrayList<>();
        response = testRestTemplate.getForObject(getApiUrl() + "?name=EmployeeTest Updated", response.getClass());

        assertNotNull(response);
        assertNotEquals(response.size(), 0);

        Map<String, Object> map = response.get(response.size() - 1);
        System.out.println(map);

        testRestTemplate.delete(getApiUrl() + "/" + map.get("id"));
        response = testRestTemplate.getForObject(getApiUrl() + "?name=EmployeeTest Updated", response.getClass());

        System.out.println(response);
        assertNotNull(response);
        assertEquals(response.size(), 0);
    }

    @Test
    @Order(9)
    void testDeleteEmployeeShouldFailed() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Employee> requestEntity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = testRestTemplate.exchange(getApiUrl() + "/0", HttpMethod.DELETE, requestEntity, String.class);

        System.out.println(response);
        assertTrue(response.getStatusCode().is5xxServerError());
        assertNotNull(response.getBody());
    }
}