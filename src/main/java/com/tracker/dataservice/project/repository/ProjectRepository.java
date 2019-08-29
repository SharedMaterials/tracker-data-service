package com.tracker.dataservice.project.repository;

import com.tracker.dataservice.project.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("select p from Project p where p.activeInd = 1 and p.groupId = :groupId")
    List<Project> findActiveProjectsByGroupId(@Param("groupId") Long groupId);

}
