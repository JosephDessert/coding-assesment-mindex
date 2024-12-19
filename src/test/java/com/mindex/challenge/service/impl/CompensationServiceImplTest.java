package com.mindex.challenge.service.impl;

import static org.junit.Assert.assertEquals;

import java.sql.Timestamp;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import com.mindex.challenge.service.EmployeeService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTest {

    private String postUrl;
    private String getAllUrl;
    private String getByEmployeeIdUrl;
    private String employeeUrl;

    @Autowired
    private CompensationService compensationService;


    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        employeeUrl = "http://localhost:" + port + "/employee";
        postUrl = "http://localhost:" + port + "/compensation";
        getAllUrl = "http://localhost:" + port + "/compensation/";
        getByEmployeeIdUrl = "http://localhost:" + port + "/compensation/{id}";
    }

    @Test
    public void testCompensationService(){

        Employee testEmployee = new Employee();
        testEmployee.setFirstName("John");
        testEmployee.setLastName("Doe");
        testEmployee.setDepartment("Engineering");
        testEmployee.setPosition("Developer");

        // Create checks
        Employee createdEmployee = restTemplate.postForEntity(employeeUrl, testEmployee, Employee.class).getBody();
        Compensation testCompensation = new Compensation();
        testCompensation.setEmployeeId(createdEmployee.getEmployeeId());
        testCompensation.setSalary(100000);
        testCompensation.setIsCurrent(true);
        testCompensation.setEffectiveDate(new Timestamp(1722772376));

        Compensation createdCompensation = restTemplate.postForEntity(postUrl, testCompensation, Compensation.class).getBody();
        assertEmployeeEquivalence(createdCompensation.getEmployee(), createdEmployee);
        assertEquals(createdCompensation.getSalary(), testCompensation.getSalary(), 0);
    }

    private static void assertEmployeeEquivalence(Employee expected, Employee actual) {
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getDepartment(), actual.getDepartment());
        assertEquals(expected.getPosition(), actual.getPosition());
    }


}
