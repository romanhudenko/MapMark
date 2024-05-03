package org.mapmark.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.mapmark.dto.RelationsDTO;
import org.mapmark.model.Mark;
import org.mapmark.service.RelationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/rel")
@RequiredArgsConstructor
public class RelationsController {

    private final RelationService relationService;


    @PostMapping("/addGroupToMark")
    public ResponseEntity<String> addGroupToMark(@RequestBody @Valid RelationsDTO ids) {
        relationService.addGroupToMark(ids);
        return new ResponseEntity<>("Saved", HttpStatus.CREATED);
    }

    @PostMapping("/removeGroupFromMark")
    public ResponseEntity<String> removeGroupFromMark(@RequestBody @Valid RelationsDTO ids) {
        relationService.removeMarkFromGroup(ids);
        return new ResponseEntity<>("Saved", HttpStatus.CREATED);
    }
}
