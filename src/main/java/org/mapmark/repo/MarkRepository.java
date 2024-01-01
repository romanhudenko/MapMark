package org.mapmark.repo;

import org.mapmark.model.Mark;
import org.mapmark.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface MarkRepository extends JpaRepository<Mark, String> {

    List<Mark> findByNameContainingAndUser_Username(String name, String username);

    List<Mark> findMarksByGroupsIdAndUser_Username(Long groupId, String username);

    List<Mark> findByUser_Username(String username);

    Mark findByIdAndUser_Username(String id, String username);

}
