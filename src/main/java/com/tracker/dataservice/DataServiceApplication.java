package com.tracker.dataservice;

import com.tracker.dataservice.employee.Employee;
import com.tracker.dataservice.employee.repository.EmployeeRepository;
import com.tracker.dataservice.group.Group;
import com.tracker.dataservice.group.repository.GroupRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class DataServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataServiceApplication.class, args);
    }

}

@Component
class DBConfig implements CommandLineRunner {

    private final GroupRepository groupRepository;
    private final EmployeeRepository employeeRepository;

    public DBConfig(GroupRepository groupRepository, EmployeeRepository employeeRepository) {
        this.groupRepository = groupRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Group group = Group.builder()
                .name("Bham N")
                .activeInd(1L)
                .build();
        group = groupRepository.save(group);
        System.out.println(groupRepository.findActiveById(group.getGroupId()));
        group = Group.builder()
                .name("Hoover")
                .activeInd(1L)
                .build();
        group = groupRepository.save(group);
        System.out.println(groupRepository.findActiveById(group.getGroupId()));

        Employee employee = Employee.builder()
                .groupId(1L)
                .name("John A. Doe")
                .position("Associate")
                .email("john@fakedomain.com")
                .activeInd(1L)
                .build();
        employee = employeeRepository.save(employee);
        System.out.println(employeeRepository.getActiveById(employee.getEmployeeId()));
        employee = Employee.builder()
                .groupId(2L)
                .name("Jane B. Doe")
                .position("Contractor")
                .email("jane@fakedomain.com")
                .activeInd(1L)
                .build();
        employee = employeeRepository.save(employee);
        System.out.println(employeeRepository.getActiveById(employee.getEmployeeId()));
    }
}
