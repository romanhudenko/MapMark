package org.mapmark.web;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.mapmark.dto.MarkDTO;
import org.mapmark.model.Mark;
import org.mapmark.security.service.UserDetailsImpl;
import org.mapmark.service.MarkService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/mark")
@RequiredArgsConstructor
public class MarkController {

    private final MarkService markService;


    /**
     * Get all marks
     *
     * @return Array of marks
     */
    @GetMapping
    public ResponseEntity<List<Mark>> getMarks() {
        return new ResponseEntity<>(markService.getMarks(), HttpStatus.OK);
    }

    /**
     * Get mark by UUID
     *
     * @param uuid mark ID
     * @return single mark
     */

    @GetMapping("/{uuid}")
    public ResponseEntity<Mark> getMarkById(@PathVariable String uuid) {
        return new ResponseEntity<>(markService.getMarkByUUID(uuid), HttpStatus.OK);
    }

    /**
     * Get list of marks search by name
     *
     * @param name part of mark name
     * @return Array of marks
     */

    @GetMapping("/name/{name}")
    public ResponseEntity<List<Mark>> getMarkByName(@PathVariable String name) {
        return new ResponseEntity<>(markService.getByName(name), HttpStatus.OK);
    }

    /**
     * Get list of marks in group
     *
     * @param groupId group ID
     * @return Array of marks in this group
     */

    @GetMapping("/in/{groupId}")
    public ResponseEntity<List<Mark>> getMarksInGroup(@PathVariable Long groupId) {
        return new ResponseEntity<>(markService.getMarksInGroup(groupId), HttpStatus.OK);
    }

    /**
     * Create new mark
     *
     * @param markDTO JSON in body
     * @return Status
     */
    @PostMapping
    public ResponseEntity<Mark> addMark(@RequestBody @Valid MarkDTO markDTO) {
        return new ResponseEntity<>(markService.save(markDTO), HttpStatus.CREATED);
    }

    /**
     * Edit mark
     *
     * @param uuid    mark ID
     * @param markDTO new data
     * @return edited mark entity
     */
    @PutMapping("/{uuid}")
    public ResponseEntity<Mark> updateMark(@PathVariable String uuid, @Valid @RequestBody MarkDTO markDTO) {
        return new ResponseEntity<>(markService.update(uuid, markDTO), HttpStatus.OK);

    }

    /**
     * Delete mark by UUID
     *
     * @param id mark uuid
     */

    @DeleteMapping("/{uuid}")
    public void deleteMark(@PathVariable String uuid) {
        markService.remove(uuid);
    }

}


