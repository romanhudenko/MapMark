package org.mapmark.web;


import jakarta.validation.Valid;
import org.mapmark.dto.MarkDTO;
import org.mapmark.model.Mark;
import org.mapmark.service.MarkService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("mark")
public class MarkController {

    private final MarkService markService;

    public MarkController(MarkService markService) {
        this.markService = markService;
    }


    /**
     * Get all marks
     *
     * @return Array of marks
     */
    @GetMapping
    public ResponseEntity<List<Mark>> getMarks() {
        return new ResponseEntity<>(markService.get(), HttpStatus.OK);
    }

    /**
     * Get mark by UUID
     *
     * @param uuid mark ID
     * @return single mark
     */

    @GetMapping("/{uuid}")
    public ResponseEntity<Mark> getMarkById(@PathVariable String uuid) {
        Mark mark = markService.get(uuid);
        if (mark == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(mark, HttpStatus.OK);
    }

    /**
     * Get list of marks search by name
     *
     * @param name part of mark name
     * @return Array of marks
     */

    @GetMapping("/name/{name}")
    public ResponseEntity<List<Mark>> getMarkByName(@PathVariable String name) {
        List<Mark> mark = markService.getByName(name);
        if (mark == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(mark, HttpStatus.OK);
    }

    /**
     * Get list of marks in group
     *
     * @param groupId group ID
     * @return Array of marks in this group
     */

    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<Mark>> getMarksInGroup(@PathVariable Long groupId) {
        List<Mark> mark = markService.getMarksInGroup(groupId);
        if (mark == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(mark, HttpStatus.OK);
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
     * @param uuid mark ID
     * @param markDTO new data
     * @return edited mark entity
     */
    @PutMapping("/{uuid}")
    public ResponseEntity<Mark> updateMark(@PathVariable String uuid, @RequestBody @Valid MarkDTO markDTO) {

        Mark mark = markService.update(uuid, markDTO);
        if (mark == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(mark, HttpStatus.OK);

    }

    /**
     * Delete mark by UUID
     * @param id mark uuid
     */

    @DeleteMapping("/{id}")
    public void deleteMark(@PathVariable String id) {
        markService.remove(id);
    }

}


