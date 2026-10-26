package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee create(Employee employee) {
        LOG.debug("Creating employee [{}]", employee);

        employee.setEmployeeId(UUID.randomUUID().toString());
        employeeRepository.insert(employee);

        return employee;
    }

    @Override
    public Employee read(String id) {
        LOG.debug("Creating employee with id [{}]", id);

        Employee employee = employeeRepository.findByEmployeeId(id);

        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        return employee;
    }

    @Override
    public Employee update(Employee employee) {
        LOG.debug("Updating employee [{}]", employee);

        return employeeRepository.save(employee);
    }
    
    @Override
    public ReportingStructure getReportNumber(String id) {
        LOG.debug("Getting number of reporters for employee with id: [{}]", id);

        Employee employee = employeeRepository.findByEmployeeId(id);
        int reportsCnt = getTotalReports(employee);

        return new ReportingStructure(employee, reportsCnt);
    }

    private int getTotalReports(Employee employee) {

        List<Employee> reporters = employee.getDirectReports();
        if (reporters == null){
            return 0; 
        }

        int reporterCnt = 0;

        for (Employee reporter : reporters) {
            reporter = employeeRepository.findByEmployeeId(reporter.getEmployeeId());
            reporterCnt++;
            if (reporter.getDirectReports() != null) {
                reporterCnt+=getTotalReports(reporter);
            }
        }

        return reporterCnt;
    }

}
