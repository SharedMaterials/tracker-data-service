package com.tracker.dataservice.employee.service;

import com.tracker.dataservice.employee.Employee;
import com.tracker.dataservice.employee.model.EmployeeRequest;

import java.util.List;

public interface EmployeeService {

    List<Employee> getAll();

    Employee getById( Long employeeId );

    Employee saveEmployee( EmployeeRequest employeeRequest );

    Employee updateEmployee( Long employeeId, EmployeeRequest employeeRequest );

    void inactivateEmployee( Long employeeId );

    List<Employee> findByGroupId( Long groupId );

    Employee addToGroup( Long employeeId, Long groupId );

    Employee removeFromGroup( Long employeeId );

    List<Employee> notInGroup( Long groupId );

    void releaseEmployees(Long groupId);
}
