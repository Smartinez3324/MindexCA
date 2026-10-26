package com.mindex.challenge.data;

public class Compensation {
    private int salary;
    private String employeeId;
    private String effectiveDate; // YYYY-MM-DD

    public Compensation() {
    }

    public int getSalary() {
        return salary;
    }
    
    public String getEmployeeId() {
        return employeeId;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
}
