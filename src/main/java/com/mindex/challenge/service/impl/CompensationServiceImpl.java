package com.mindex.challenge.service.impl;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import com.mindex.challenge.service.EmployeeService;

@Service
public class CompensationServiceImpl implements CompensationService {

    private static final Logger LOG = LoggerFactory.getLogger(CompensationService.class);

    @Autowired
    private CompensationRepository compensationRepository;

    @Autowired
    private EmployeeService employeeService;

    @Override
    public List<Compensation> readAll() {
        LOG.debug("Read all compensation objects in database.");
        return compensationRepository.findAll();
    }

    @Override
    public List<Compensation> readAllByEmployeeId(String id) {
        LOG.debug("Read the compensation value of employee [{}]", id);
        return compensationRepository.findAllByEmployeeId(id);
    }

    @Override
    public Compensation create(Compensation compensation) {
        LOG.debug("Create a compensation entry associated with an enployee.");

        Employee employee = employeeService.read(compensation.getEmployeeId());

        if (employee == null) {
            LOG.error("Compensation must be attached to valid employee. No employee found with id [{}]",
                    compensation.getEmployeeId());
            throw new HttpClientErrorException(HttpStatusCode.valueOf(400));
        }
        compensation.setCompensationId(UUID.randomUUID().toString());
        compensation.setEmployee(employee);
        compensationRepository.insert(compensation);

        return compensation;
    }

}
