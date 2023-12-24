package org.mapmark.repo;

import org.mapmark.model.Mark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface MarkRepository extends JpaRepository<Mark, String> {



    List<Mark> findByNameContaining(String name);

    List<Mark> findMarksByGroupsId(Long groupId);
}
