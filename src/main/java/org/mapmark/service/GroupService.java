package org.mapmark.service;

import lombok.RequiredArgsConstructor;
import org.mapmark.dto.GroupDTO;
import org.mapmark.model.Group;
import org.mapmark.repo.GroupRepository;
import org.mapmark.security.config.AuthFacadeImpl;
import org.mapmark.util.exceptions.DataNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final AuthFacadeImpl authFacade;
    private final GroupRepository groupRepository;


    public List<Group> getGroups() {
        return groupRepository.findByUser_Username(authFacade.getUsername());
    }


    public Group getGroupById(Long id) {
        Group group = groupRepository.findByIdAndUser_Username(id, authFacade.getUsername());
        if (group == null) throw new DataNotFoundException("Group with id " + id + " not found");
        return group;
    }

    public List<Group> getGroupsByName(String name) {
        return groupRepository.findByNameContainingAndUser_Username(name, authFacade.getUsername());
    }


    public List<Group> getGroupsByMarkId(String markId) {
        return groupRepository.findGroupsByMarksIdAndUser_Username(markId, authFacade.getUsername());
    }


    public void remove(Long id) {
        Group group = groupRepository.findByIdAndUser_Username(id, authFacade.getUsername());
        if (group != null) groupRepository.deleteById(id);

    }

    public Group save(GroupDTO groupDTO) {

        LocalDateTime timestamp = LocalDateTime.now();

        String desc = groupDTO.getDescription().isBlank() ? "" : groupDTO.getDescription();
        //fixme tmp ico
        byte[] a = {33};

        Group group = Group.builder()
                .name(groupDTO.getName())
                .description(desc)
                .user(authFacade.getAuthenticatedUserEntity())
                //fixme default icon
                .icon(a)
                .CreateTimestamp(timestamp)
                .UpdateTimestamp(timestamp)
                .build();

        return groupRepository.save(group);

    }

    public Group update(Long id, GroupDTO groupDTO) {

        Group group = groupRepository.findByIdAndUser_Username(id, authFacade.getUsername());
        if (group == null) throw new DataNotFoundException("Group with id " + id + " not found");

        if (!groupDTO.getName().isBlank()) {
            group.setName(groupDTO.getName());
        }

        if (!groupDTO.getDescription().isBlank()) {
            group.setDescription(groupDTO.getDescription());
        }

        //fixme default icon
        byte[] a = {33};
        group.setIcon(a);


        group.setUpdateTimestamp(LocalDateTime.now());

        return groupRepository.save(group);
    }


}
