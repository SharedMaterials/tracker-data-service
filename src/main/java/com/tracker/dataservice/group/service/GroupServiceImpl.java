package com.tracker.dataservice.group.service;

import com.tracker.dataservice.group.Group;
import com.tracker.dataservice.group.model.GroupRequest;
import com.tracker.dataservice.group.repository.GroupRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;

    public GroupServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public List<Group> getAll() {

        List<Group> groups = groupRepository.getAllActive();
        if (groups.isEmpty()) {
            throw new EntityNotFoundException("No active groups found");
        } else {
            return groups;
        }
    }

    @Override
    public Group getById(Long groupId) {

        Group group = groupRepository.findActiveById(groupId);
        if (group == null) {
            throw new EntityNotFoundException("No active group found for id :: " + groupId);
        } else {
            return group;
        }
    }

    @Override
    public Group saveGroup(GroupRequest groupRequest) {

        Group group = new Group().builder()
                .name(groupRequest.getName())
                .activeInd(1L)
                .build();

        return groupRepository.save(group);
    }

    @Override
    public Group updateGroup(Long groupId, GroupRequest groupRequest) {

        Group group = getById(groupId);
        if (groupRequest.getName() != null) {
            group.setName(groupRequest.getName());
        }
        return groupRepository.save(group);
    }

    @Override
    public void inactivateGroup(Long groupId) {

        Group group = getById(groupId);
        group.setActiveInd(0L);
        groupRepository.save(group);
    }
}
