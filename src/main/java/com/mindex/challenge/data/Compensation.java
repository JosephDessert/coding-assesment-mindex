package com.mindex.challenge.data;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.format.annotation.DateTimeFormat;

public class Compensation {

    private String compensationId;
    private String employeeId;
    private double salary;
    // @CreatedDate
    private Date effectiveDate;
    private Boolean isCurrent;

    @DBRef
    private Employee employee;

    public Compensation() {

    }

    public void setCompensationId(String compensationId) {
        this.compensationId = compensationId;
    }

    public String getCompensationId() {
        return this.compensationId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeId() {
        return this.employeeId;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getSalary() {
        return this.salary;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Date getEffectiveDate() {
        return this.effectiveDate;
    }

    public void setIsCurrent(boolean isCurrent) {
        this.isCurrent = isCurrent;
    }

    public boolean getIsCurrent() {
        return this.isCurrent;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Employee getEmployee() {
        return this.employee;
    }

}
