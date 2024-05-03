package org.mapmark.service;

import lombok.RequiredArgsConstructor;
import org.mapmark.dto.RelationsDTO;
import org.mapmark.model.Group;
import org.mapmark.model.Mark;
import org.mapmark.repo.GroupRepository;
import org.mapmark.repo.MarkRepository;
import org.mapmark.security.config.AuthFacadeImpl;
import org.mapmark.util.exceptions.DataNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RelationService {

    private final GroupRepository groupRepository;
    private final MarkRepository markRepository;
    private final AuthFacadeImpl authFacade;


    public Mark addGroupToMark(RelationsDTO ids) {

        Group group = getGroupEntity(ids.getGroupId());
        if (group == null) throw new DataNotFoundException("Group with id " + ids.getGroupId() + " not found");

        Mark mark = getMarkEntity(ids.getMarkId());
        if (mark == null)  throw new DataNotFoundException("Mark with id " + ids.getMarkId() + " not found");

        mark.addGroup(group);
        return markRepository.save(mark);

    }

    public Mark removeMarkFromGroup(RelationsDTO ids) {

        Mark mark = getMarkEntity(ids.getMarkId());
        if (mark == null) throw new DataNotFoundException("Mark with id " + ids.getMarkId() + " not found");;

        mark.removeGroup(ids.getGroupId());
        return markRepository.save(mark);

    }

    private Mark getMarkEntity(String id) {
        return markRepository.findByIdAndUser_Username(id, authFacade.getUsername());
    }

    private Group getGroupEntity(Long id) {
        return groupRepository.findByIdAndUser_Username(id, authFacade.getUsername());
    }

}
