package org.mapmark.repo;

import org.mapmark.model.Group;
import org.mapmark.model.Mark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface GroupRepository extends JpaRepository<Group, Long> {

    List<Group> findGroupsByMarksId(String markId);

    List<Group> findByNameContaining(String name);
}
