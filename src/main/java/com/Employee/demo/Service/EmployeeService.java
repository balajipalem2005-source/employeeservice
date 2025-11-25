package com.Employee.demo.Service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Employee.demo.Entity.Employee;
import com.Employee.demo.feignclient.AddressClient;
import com.Employee.demo.repository.EmployeeRepo;
import com.Employee.demo.response.AddressResponse;
import com.Employee.demo.response.EmployeeResponse;

@Service
public class EmployeeService {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private AddressClient addressClient;

    public EmployeeResponse getEmployeeById(int id) {
        log.info("Fetching employee details for ID: {}", id);

        Optional<Employee> employeeOpt = employeeRepo.findById(id);

        if (!employeeOpt.isPresent()) {
            log.warn("No employee found with ID: {}", id);
            return null; // or throw a custom exception
        }

        Employee employee = employeeOpt.get();
        log.debug("Employee entity fetched: {}", employee);

        EmployeeResponse employeeResponse = mapper.map(employee, EmployeeResponse.class);
        log.debug("Mapped EmployeeResponse created: {}", employeeResponse);

        log.info("Calling AddressClient to fetch address for employeeId: {}", id);
        ResponseEntity<AddressResponse> addressResponse = addressClient.getAddressByEmployeeId(id);

        if (addressResponse.getBody() != null) {
            employeeResponse.setAddressResponse(addressResponse.getBody());
            log.info("AddressResponse successfully attached for employeeId: {}", id);
        } else {
            log.warn("AddressResponse is null for employeeId: {}", id);
        }

        log.info("Final EmployeeResponse prepared successfully for ID: {}", id);
        return employeeResponse;
    }
}