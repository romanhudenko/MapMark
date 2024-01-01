package org.mapmark.repo;

import org.mapmark.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface GroupRepository extends JpaRepository<Group, Long> {


    Group findByIdAndUser_Username(Long id, String username);

    List<Group> findByUser_Username(String username);

    List<Group> findGroupsByMarksIdAndUser_Username(String markId,String username);

    List<Group> findByNameContainingAndUser_Username(String name, String username);

}
