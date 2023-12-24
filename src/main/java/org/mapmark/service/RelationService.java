package org.mapmark.service;

import org.mapmark.dto.RelationsDTO;
import org.mapmark.model.Group;
import org.mapmark.model.Mark;
import org.mapmark.repo.GroupRepository;
import org.mapmark.repo.MarkRepository;
import org.springframework.stereotype.Service;

@Service
//fixme REFACTOR THIS
public class RelationService {

    private final GroupRepository groupRepository;
    private final MarkRepository markRepository;

    public RelationService(GroupRepository groupRepository, MarkRepository markRepository) {
        this.groupRepository = groupRepository;
        this.markRepository = markRepository;
    }

    public Mark addGroupToMark(RelationsDTO ids) {

        Group group = getGroupEntity(ids.getGroupId());
        if (group == null) return null;

        Mark mark = getMarkEntity(ids.getMarkId());
        if (mark == null) return null;

        mark.addGroup(group);
        return markRepository.save(mark);

    }

    public Mark removeMarkFromGroup(RelationsDTO ids) {

        Mark mark = getMarkEntity(ids.getMarkId());
        if (mark == null) return null;

        mark.removeGroup(ids.getGroupId());
        return markRepository.save(mark);

    }

    private Mark getMarkEntity(String id) {
        return markRepository.findById(id).orElse(null);
    }

    private Group getGroupEntity(Long id) {
        return groupRepository.findById(id).orElse(null);
    }


}
