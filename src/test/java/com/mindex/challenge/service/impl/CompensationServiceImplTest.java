package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Compensation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTest {

    // Seed data ids from employee_database.json
    private static final String JOHN_ID = "16a596ae-edd3-4847-99fe-c4518e82c86f";
    private static final String PAUL_ID = "b7839309-3348-463b-a7e3-5de1c168beb3";

    private String compensationUrl;
    private String compensationIdUrl;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        compensationUrl = "http://localhost:" + port + "/compensation";
        compensationIdUrl = "http://localhost:" + port + "/compensation/{id}";
    }

    @Test
    public void testCreate() {
        Compensation compensation = buildCompensation(JOHN_ID, 85000, "2026-03-14");

        Compensation created = restTemplate
                .postForEntity(compensationUrl, compensation, Compensation.class)
                .getBody();

        assertNotNull(created);
        assertCompensationEquivalence(compensation, created);
    }

    @Test
    public void testCreateAndRead() {
        Compensation compensation = buildCompensation(PAUL_ID, 72000, "2026-05-01");

        // Create
        restTemplate.postForEntity(compensationUrl, compensation, Compensation.class);

        // Read it back by employee id
        Compensation read = restTemplate
                .getForEntity(compensationIdUrl, Compensation.class, PAUL_ID)
                .getBody();

        assertNotNull(read);
        assertCompensationEquivalence(compensation, read);
    }

    private static Compensation buildCompensation(String employeeId, int salary, String effectiveDate) {
        Compensation compensation = new Compensation();
        compensation.setEmployeeId(employeeId);
        compensation.setSalary(salary);
        compensation.setEffectiveDate(effectiveDate);
        return compensation;
    }

    private static void assertCompensationEquivalence(Compensation expected, Compensation actual) {
        assertEquals(expected.getEmployeeId(), actual.getEmployeeId());
        assertEquals(expected.getSalary(), actual.getSalary());
        assertEquals(expected.getEffectiveDate(), actual.getEffectiveDate());
    }
}
