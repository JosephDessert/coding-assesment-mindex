package com.mindex.challenge.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.CompensationService;
import com.mindex.challenge.service.ReportingStructureService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportingStructureServiceImplTest {

    private String directReportsUrl;
    private String employeeUrl;


    @Autowired
    private ReportingStructureService reportingStructureService;


    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        employeeUrl = "http://localhost:" + port + "/employee";
        directReportsUrl = "http://localhost:" + port + "/compensation";
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

        Employee testEmployee2 = new Employee();
        testEmployee2.setFirstName("John2");
        testEmployee2.setLastName("Doe2");
        testEmployee2.setDepartment("Engineering");
        testEmployee2.setPosition("Developer");

        List<Employee> reports = new ArrayList<Employee>();
        reports.add(createdEmployee);
        testEmployee2.setDirectReports(reports);

        Employee createdEmployee2 = restTemplate.postForEntity(employeeUrl, testEmployee2, Employee.class).getBody();

        assertNotNull(createdEmployee2.getEmployeeId());
        assertNotNull(createdEmployee2.getDirectReports());

        ReportingStructure testReportingStructure = new ReportingStructure(createdEmployee2, 1);
        ReportingStructure testReportingStructure2 = reportingStructureService.read(createdEmployee2.getEmployeeId());
        assertEquals(testReportingStructure.getNumberOfReports(), testReportingStructure2.getNumberOfReports());
        assertEmployeeEquivalence(testReportingStructure.getEmployee(), testReportingStructure2.getEmployee());
    }

    private static void assertEmployeeEquivalence(Employee expected, Employee actual) {
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getDepartment(), actual.getDepartment());
        assertEquals(expected.getPosition(), actual.getPosition());
    }
}
