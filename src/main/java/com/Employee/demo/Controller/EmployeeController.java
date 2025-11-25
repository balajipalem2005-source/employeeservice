package com.Employee.demo.Controller;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Employee.demo.Service.EmployeeService;
import com.Employee.demo.response.EmployeeResponse;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getEmployeeDetails(@PathVariable("id") int id) {
        log.info("Received API request: GET /employees/{}", id);
//this is get mapping
        EmployeeResponse employee = employeeService.getEmployeeById(id);

        if (employee == null) {
            log.warn("No data found for employeeId: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        log.info("Returning employee details for employeeId: {}", id);
        log.debug("Response body: {}", employee);

        return ResponseEntity.status(HttpStatus.OK).body(employee);
    }
}
