package com.tracker.dataservice.group.controller;

import com.tracker.dataservice.employee.Employee;
import com.tracker.dataservice.employee.service.EmployeeService;
import com.tracker.dataservice.group.Group;
import com.tracker.dataservice.group.model.GroupRequest;
import com.tracker.dataservice.group.service.GroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/api")
public class GroupController {

    private final GroupService groupService;
    private final EmployeeService employeeService;

    public GroupController(GroupService groupService, EmployeeService employeeService) {
        this.groupService = groupService;
        this.employeeService = employeeService;
    }


    @GetMapping("/groups")
    public ResponseEntity<List<Group>> getAll() {

        List<Group> groupList = groupService.getAll();
        return ResponseEntity.ok(groupList);

    }

    @GetMapping("/group/{groupId}")
    public ResponseEntity<Group> getById(@PathVariable Long groupId) {

        Group group = groupService.getById(groupId);
        return ResponseEntity.ok(group);
    }

    @PostMapping("/group")
    public ResponseEntity<Group> saveGroup(@RequestBody GroupRequest groupRequest) {

        Group group = groupService.saveGroup(groupRequest);
        return ResponseEntity.created(URI.create("/api/group/" + group.getGroupId())).body(group);
    }

    @PutMapping("/group/{groupId}")
    public ResponseEntity<Group> updateGroup(@PathVariable Long groupId, @RequestBody GroupRequest groupRequest) {

        Group group = groupService.updateGroup(groupId, groupRequest);
        return ResponseEntity.accepted().body(group);
    }

    @DeleteMapping("/group/{groupId}")
    public ResponseEntity<?> inactivateGroup(@PathVariable Long groupId) {

        try {
            employeeService.releaseEmployees(groupId);
        } catch (EntityNotFoundException e) {
            /* No Employees found for this group, continue */
        }
        groupService.inactivateGroup(groupId);
        return ResponseEntity.accepted().build();
    }

    @PutMapping("/group/{groupId}/employee/{employeeId}")
    public ResponseEntity addEmployeeToGroup(@PathVariable Long groupId, @PathVariable Long employeeId) {

        Employee employee = employeeService.addToGroup(employeeId, groupId);
        return ResponseEntity.accepted().body(employee);
    }

    @PutMapping("/group/employee/{employeeId}")
    public ResponseEntity removeEmployeeFromGroup(@PathVariable Long employeeId) {

        Employee employee = employeeService.removeFromGroup(employeeId);
        return ResponseEntity.accepted().body(employee);
    }
}
