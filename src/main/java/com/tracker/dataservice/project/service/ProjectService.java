package com.tracker.dataservice.project.service;

import com.tracker.dataservice.project.Project;
import com.tracker.dataservice.project.model.ProjectRequest;

import java.util.List;

public interface ProjectService {

    List<Project> getAll();

    Project getById(Long projectId);

    Project saveNew(ProjectRequest projectRequest);

    Project updateById(Long projectId, ProjectRequest projectRequest);

    List<Project> getByGroupId(Long groupId);

    void deleteById(Long projectId);
}
