package org.mapmark.service;

import org.mapmark.dto.GroupDTO;
import org.mapmark.model.Group;
import org.mapmark.repo.GroupRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GroupService {


    private final GroupRepository groupRepository;

    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public List<Group> get() {
        return groupRepository.findAll();
    }


    public List<Group> getGroupsByMarkId(String markId) {
        return groupRepository.findGroupsByMarksId(markId);
    }

    public Group get(Long id) {
        return groupRepository.findById(id).orElse(null);
    }

    public List<Group> getGroupsByName(String name) {
        return groupRepository.findByNameContaining(name);
    }


    public void remove(Long id) {
        groupRepository.deleteById(id);
    }

    public Group save(GroupDTO groupDTO) {


        LocalDateTime timestamp = LocalDateTime.now();

        String desc = groupDTO.getDescription() == null ? "" : groupDTO.getDescription();
        //fixme tmp ico
        byte[] a = {33};

        Group group = Group.builder()
                .name(groupDTO.getName())
                .description(desc)
//                .userId(1)
//                .icon(groupOfMarksDTO.getIco().getBytes())
                .icon(a)
                .CreateTimestamp(timestamp)
                .UpdateTimestamp(timestamp)
                .build();

        return groupRepository.save(group);

    }

    public Group update(Long id, GroupDTO groupDTO) {

        Group group = groupRepository.findById(id).orElse(null);
        if (group == null) return null;

        String desc = groupDTO.getDescription() == null ? "" : groupDTO.getDescription();
        //fixme tmp ico
        byte[] a = {33};

        group.setName(groupDTO.getName());
        group.setDescription(desc);
        group.setIcon(a);
        group.setUpdateTimestamp(LocalDateTime.now());

        return groupRepository.save(group);
    }


}
