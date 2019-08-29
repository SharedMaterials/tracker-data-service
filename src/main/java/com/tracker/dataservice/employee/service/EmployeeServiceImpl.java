package com.tracker.dataservice.employee.service;

import com.tracker.dataservice.employee.Employee;
import com.tracker.dataservice.employee.model.EmployeeRequest;
import com.tracker.dataservice.employee.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl( EmployeeRepository employeeRepository ) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> getAll() {

        List<Employee> employeeList = employeeRepository.getAllActive();
        if ( employeeList.isEmpty() ) {

            throw new EntityNotFoundException("No active employees found");
        } else {

            return employeeList;
        }
    }

    @Override
    public Employee getById( Long employeeId ) {

        Employee employee = employeeRepository.getActiveById( employeeId );
        if ( employee == null ) {

            throw new EntityNotFoundException("No active employee found with id :: " + employeeId);
        } else {

            return employee;
        }
    }

    @Override
    public Employee saveEmployee( EmployeeRequest employeeRequest ) {

        Employee employee = new Employee().builder()
                .groupId( 0L )
                .name( employeeRequest.getName() )
                .position( employeeRequest.getPosition() )
                .email( employeeRequest.getEmail() )
                .activeInd( 1L )
                .build();



        return employeeRepository.save( employee );
    }

    @Override
    public Employee updateEmployee( Long employeeId, EmployeeRequest employeeRequest ) {

        Employee employee = getById( employeeId );
        if ( employeeRequest.getGroupId() != null ) {

            employee.setGroupId( employee.getEmployeeId() );
        }
        if ( employeeRequest.getName() != null ) {

            employee.setName( employeeRequest.getName() );
        }
        if ( employeeRequest.getPosition() != null ) {

            employee.setPosition( employeeRequest.getPosition() );
        }
        if ( employeeRequest.getEmail() != null ) {

            employee.setEmail( employeeRequest.getEmail() );
        }


        return employeeRepository.save( employee );
    }

    @Override
    public void inactivateEmployee( Long employeeId ) {

        Employee employee = getById( employeeId );
        employee.setActiveInd( 0L );
        employeeRepository.save( employee );
    }

    @Override
    public List<Employee> findByGroupId( Long groupId ) {

        List<Employee> employeeList = employeeRepository.getActiveByGroupId( groupId );
        if ( employeeList.isEmpty() ) {
            String details = (groupId == 0) ? "No ungrouped employees found" : "No employees found for this group id :: " + groupId;
            throw new EntityNotFoundException(details);
        } else {

            return employeeList;
        }
    }

    @Override
    public Employee addToGroup( Long employeeId, Long groupId ) {

        Employee employee = getById( employeeId );
        employee.setGroupId( groupId );
        return employeeRepository.save( employee );
    }

    public Employee removeFromGroup( Long employeeId ) {

        Employee employee = getById( employeeId );
        employee.setGroupId( 0L );
        return employeeRepository.save( employee );
    }

    public List<Employee> notInGroup( Long groupId ) {

        List<Employee> employeeList = employeeRepository.getActiveNotWithinGroup( groupId );
        if ( employeeList.isEmpty() ) {

            throw new EntityNotFoundException("No employees found without the group with id :: " + groupId);
        } else {

            return employeeList;
        }
    }

    @Override
    public void releaseEmployees( Long groupId ) {

        List<Employee> employeeList =
                findByGroupId(groupId)
                .stream()
                .map(employee -> {
                    employee.setGroupId(0L);
                    return employee;
                })
                .collect(Collectors.toList());

        employeeRepository.saveAll(employeeList);
    }
}
