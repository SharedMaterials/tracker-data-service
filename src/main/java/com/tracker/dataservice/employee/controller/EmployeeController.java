package com.tracker.dataservice.employee.controller;

import com.tracker.dataservice.employee.Employee;
import com.tracker.dataservice.employee.model.EmployeeRequest;
import com.tracker.dataservice.employee.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping( "/api" )
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController( EmployeeService employeeService ) {

        this.employeeService = employeeService;
    }

    @GetMapping( "/employees" )
    public ResponseEntity<List<Employee>> getAll() {

        List<Employee> employeeList = employeeService.getAll();
        return ResponseEntity.ok( employeeList );
    }

    @GetMapping( "/employee/{employeeId}" )
    public ResponseEntity<Employee> getById( @PathVariable Long employeeId ) {

        Employee employee = employeeService.getById( employeeId );
        return ResponseEntity.ok( employee );
    }

    @PostMapping( "/employee" )
    public ResponseEntity<Employee> saveEmployee( @RequestBody EmployeeRequest employeeRequest ) {

        Employee employee = employeeService.saveEmployee( employeeRequest );
        return ResponseEntity.created( URI.create( "/api/employee/" + employee.getEmployeeId() ) ).body( employee );
    }

    @PutMapping( "/employee/{employeeId}" )
    public ResponseEntity<Employee> updateEmployee( @PathVariable Long employeeId, @RequestBody EmployeeRequest employeeRequest ) {

        Employee employee = employeeService.updateEmployee( employeeId, employeeRequest );
        return ResponseEntity.accepted().body( employee );
    }

    @DeleteMapping( "/employee/{employeeId}" )
    public ResponseEntity<?> inactivateEmployee( @PathVariable Long employeeId ) {

        employeeService.inactivateEmployee( employeeId );
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/employees/search/group/{groupId}")
    public ResponseEntity<List<Employee>> findByGroupId( @PathVariable Long groupId ) {

        List<Employee> employeeList = employeeService.findByGroupId( groupId );
        return ResponseEntity.ok( employeeList );
    }


}
