package com.mindex.challenge.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;

@RestController
public class CompensationController {

    private static final Logger LOG = LoggerFactory.getLogger(CompensationController.class);

    @Autowired
    private CompensationService compensationService;

    @PostMapping("/compensation")
    public Compensation create(@RequestBody Compensation compensation) {
        if(compensation.getEmployeeId().isEmpty()){
            LOG.error("Null value: compensation.employeeId. Compensation must have an employee id to be created.");
            throw new HttpClientErrorException(HttpStatusCode.valueOf(400));
        }

        LOG.debug("Creating new compensation for employee [{}]", compensation.getEmployeeId());
        return compensationService.create(compensation);
    }

    @GetMapping("/compensation/")
    public List<Compensation> getAllCompensation() {
        LOG.debug("Read all compensations.");
        return compensationService.readAll();
    }

    @GetMapping("/compensation/{employeeId}")
    public List<Compensation> getEmployeeCompensation(@PathVariable String employeeId) {
        LOG.debug("Read all Compensation for employee [{}]", employeeId);
        return compensationService.readAllByEmployeeId(employeeId);
    }

}
