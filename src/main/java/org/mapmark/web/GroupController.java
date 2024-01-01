package org.mapmark.web;


import jakarta.validation.Valid;
import org.mapmark.dto.GroupDTO;
import org.mapmark.model.Group;
import org.mapmark.service.GroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/group")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }


    /**
     * Get all groups
     *
     * @return Array of groups
     */

    @GetMapping
    public ResponseEntity<List<Group>> getGroups() {
        return new ResponseEntity<>(groupService.getGroups(), HttpStatus.OK);
    }

    /**
     * Get group by ID
     *
     * @param id group ID
     * @return single group entity
     */
    @GetMapping("/{id}")
    public ResponseEntity<Group> getGroup(@PathVariable Long id) {
        Group group = groupService.getGroupById(id);
        if (group == null) throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(group, HttpStatus.OK);
    }

    /**
     * Get list of groups by Name
     *
     * @param name group name
     * @return single group entity
     */
    @GetMapping("/name/{name}")
    public ResponseEntity<List<Group>> getGroupByName(@PathVariable String name) {
        List<Group> group = groupService.getGroupsByName(name);
        if (group == null) throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(group, HttpStatus.OK);
    }

    /**
     * Get all groups by mark uuid
     *
     * @param id mark UUID
     * @return Array of group entity
     */
    @GetMapping("/mark/{id}")
    public ResponseEntity<List<Group>> getGroupsByMarkId(@PathVariable String id) {
        List<Group> group = groupService.getGroupsByMarkId(id);
        if (group == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(group, HttpStatus.OK);
    }


    /**
     * Create new group
     *
     * @param groupDTO group entity
     * @return status
     */
    @PostMapping
    public ResponseEntity<Group> createGroup(@RequestBody @Valid GroupDTO groupDTO) {
        return new ResponseEntity<>(groupService.save(groupDTO), HttpStatus.CREATED);

    }

    /**
     * Edit existed group
     *
     * @param id       Group id
     * @param groupDTO new group data
     * @return status
     */

    @PutMapping("/{id}")
    public ResponseEntity<Group> updateGroup(@PathVariable Long id, @RequestBody @Valid GroupDTO groupDTO) {
        Group group = groupService.update(id, groupDTO);
        if (group == null) throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(group, HttpStatus.OK);

    }


    /**
     * Delete group by ID
     *
     * @param id group id
     * @return status
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGroup(@PathVariable Long id) {
        groupService.remove(id);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }


}


