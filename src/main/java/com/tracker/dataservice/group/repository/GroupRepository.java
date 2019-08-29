package com.tracker.dataservice.group.repository;

import com.tracker.dataservice.group.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    @Query("select g from Group g where g.activeInd = 1")
    List<Group> getAllActive();

    @Query("select g from Group g where g.activeInd = 1 and g.groupId = :groupId")
    Group findActiveById(@Param("groupId") Long groupId);

}
