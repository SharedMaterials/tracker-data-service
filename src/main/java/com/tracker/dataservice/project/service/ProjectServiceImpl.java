package com.tracker.dataservice.project.service;

import com.tracker.dataservice.project.Project;
import com.tracker.dataservice.project.model.ProjectRequest;
import com.tracker.dataservice.project.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public List<Project> getAll() {
        List<Project> projectsList = projectRepository.findAll();
        if (projectsList.isEmpty()) {
            throw new EntityNotFoundException("No projects found");
        } else {
            return projectsList;
        }
    }

    @Override
    public Project getById(Long projectId) {

        Project project = projectRepository.findById(projectId).orElse(null);
        if (project == null) {
            throw new EntityNotFoundException("No project found for id :: " + projectId);
        } else {
            return project;
        }
    }

    @Override
    public Project saveNew(ProjectRequest projectRequest) {

        Project project = Project.builder()
                .title(projectRequest.getTitle())
                .description(projectRequest.getDescription())
                .budget(projectRequest.getBudget())
                .expenseTotal(0L)
                .activeInd(1L)
                .build();

        return projectRepository.save(project);
    }

    @Override
    public Project updateById(Long projectId, ProjectRequest projectRequest) {

        Project project = getById(projectId);
        if (projectRequest.getTitle() != null && !projectRequest.getTitle().isEmpty()){
            project.setTitle(projectRequest.getTitle());
        }
        if (projectRequest.getDescription() != null && !projectRequest.getDescription().isEmpty()){
            project.setDescription(projectRequest.getDescription());
        }
        if (projectRequest.getBudget() != null && projectRequest.getBudget() > 0) {
            project.setBudget(projectRequest.getBudget());
        }
        return projectRepository.save(project);
    }

    @Override
    public void deleteById(Long projectId) {

        Project project = getById(projectId);
        project.setActiveInd(0L);
        projectRepository.save(project);
    }

    @Override
    public Project addToGroup(Long projectId, Long groupId) {

        Project project = getById(projectId);
        project.setGroupId(groupId);
        return projectRepository.save(project);
    }

    @Override
    public Project removeFromGroup(Long projectId) {

        Project project = getById(projectId);
        project.setGroupId(0L);
        return projectRepository.save(project);
    }

    @Override
    public List<Project> getByGroupId(Long groupId) {

        List<Project> projectList = projectRepository.findActiveProjectsByGroupId(groupId);
        if (projectList.isEmpty()) {
            throw new EntityNotFoundException("No projects found for this group");
        } else {
            return projectList;
        }
    }
}
