package com.tracker.dataservice.group.service;

import com.tracker.dataservice.group.Group;
import com.tracker.dataservice.group.model.GroupRequest;

import java.util.List;

public interface GroupService {

    List<Group> getAll();
    Group getById(Long groupId);
    Group saveGroup(GroupRequest groupRequest);
    Group updateGroup(Long groupId, GroupRequest groupRequest);
    void inactivateGroup(Long groupId);

}
