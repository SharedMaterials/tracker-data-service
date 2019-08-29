package com.tracker.dataservice.project.controller;

import com.tracker.dataservice.project.Project;
import com.tracker.dataservice.project.model.ProjectRequest;
import com.tracker.dataservice.project.service.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/projects")
    public ResponseEntity<List<Project>> getAll() {

        List<Project> projects = projectService.getAll();
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<Project> getById(@PathVariable Long projectId) {

        Project project = projectService.getById(projectId);
        return ResponseEntity.ok(project);
    }

    @PostMapping("/project")
    public ResponseEntity<Project> saveNew(ProjectRequest projectRequest) {

        Project project = projectService.saveNew(projectRequest);
        return ResponseEntity.created(URI.create("/api/project/" + project.getProjectId())).body(project);
    }

    @PutMapping("/project/{projectId}")
    public ResponseEntity<Project> updateById(@PathVariable Long projectId, @RequestBody ProjectRequest projectRequest) {

        Project project = projectService.updateById(projectId, projectRequest);
        return ResponseEntity.accepted().body(project);
    }

    @DeleteMapping("/project/{projectId}")
    public ResponseEntity<Object> deleteById(@PathVariable Long projectId){

        projectService.deleteById(projectId);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/projects/search/group/{groupId}")
    public ResponseEntity<List<Project>> getByGroupId(@PathVariable Long groupId) {

        List<Project> projects = projectService.getByGroupId(groupId);
        return ResponseEntity.ok(projects);
    }
}
