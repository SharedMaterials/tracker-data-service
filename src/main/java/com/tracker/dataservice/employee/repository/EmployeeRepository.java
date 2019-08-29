package com.tracker.dataservice.employee.repository;

import com.tracker.dataservice.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query( "select e from Employee e where e.activeInd = 1" )
    List<Employee> getAllActive();

    @Query( "select e from Employee e where e.activeInd = 1 and e.employeeId = :employeeId" )
    Employee getActiveById( @Param( "employeeId" ) Long employee );

    @Query( "select e from Employee e where e.activeInd = 1 and e.groupId = :groupId" )
    List<Employee> getActiveByGroupId( @Param( "groupId" ) Long groupId );

    @Query( "select e from Employee e where e.activeInd = 1 and e.groupId <> :groupId" )
    List<Employee> getActiveNotWithinGroup( @Param( "groupId" ) Long groupId );


}
