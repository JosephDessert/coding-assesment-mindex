package com.mindex.challenge.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {

    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public ReportingStructure read(String id) {
        LOG.debug("Read employee data and create reporting structure based on number of reports.");
        Employee employee = employeeRepository.findByEmployeeId(id);
        if (employee == null) {
            LOG.error("Employee was null for id [{}]", id);
            throw new HttpClientErrorException(HttpStatusCode.valueOf(400));

        }

        ReportingStructure structure = new ReportingStructure(employee);
        structure.setNumberOfReports(calculateDirectReports(employee));
        return structure;
    }

    /***
     * Recursive function to read through the direct reports of an employee object
     * and count how many a given employee below him.
     * 
     * @param employee
     * @return the count of number of reports below current employee.
     */
    private int calculateDirectReports(Employee employee) {
        int count = 0;

        if (employee.getDirectReports() != null) {
            for (Employee currentEmployee : employee.getDirectReports()) {
                count++;
                count += calculateDirectReports(currentEmployee);
            }
        }

        return count;
    }

}
