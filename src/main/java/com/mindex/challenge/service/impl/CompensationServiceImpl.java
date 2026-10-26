package com.mindex.challenge.service.impl;

import com.mindex.challenge.service.CompensationService;
import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompensationServiceImpl implements CompensationService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private CompensationRepository compensationRepository;
    
    @Autowired
    private EmployeeRepository employeeRepository;


    public Compensation create(Compensation compensation) {
        LOG.debug("Creating compensation entry [{}]", compensation); //TODO

        if (employeeRepository.findByEmployeeId(compensation.getEmployeeId()) == null) {
            // Composition is a weak entity and must have an existing employee to link to.
            // Keep in mind that when deleting employee you must delete related compensation aswell.
            throw new RuntimeException("Employee not found, must provide existing employee");
        }

        return compensationRepository.insert(compensation);
    }

    public Compensation read(String id) {
        LOG.debug("Creating compensation entry [{}]", id); //TODO
        Compensation compensation = compensationRepository.findCompensationByEmployeeId(id);

        if (compensation == null) {
            throw new RuntimeException("Compensation not found");
        }

        return compensation;
    }

}
