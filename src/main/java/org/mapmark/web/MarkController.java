package org.mapmark.web;


import jakarta.validation.Valid;
import org.mapmark.dto.MarkBasicDTO;
import org.mapmark.model.MarkBasic;
import org.mapmark.service.MarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;

@RestController
public class MarkController {

    private final MarkService markService;

    public MarkController(MarkService markService) {
        this.markService = markService;
    }


    @GetMapping("/")
    public String startPage() {
        return "hello im start page";
    }


    @GetMapping("/markbasic")
    public Iterable<MarkBasic> getMarkBasic() {
        return markService.get();
    }

    @GetMapping("/markbasic/{id}")
    public MarkBasic getMarkBasic(@PathVariable String id) {
        MarkBasic mark = markService.get(id);
        if (mark == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return mark;
    }

    @DeleteMapping("/markbasic/{id}")
    public void deleteMarkBasic(@PathVariable String id) {
        markService.remove(id);
    }

    @PostMapping("/markbasic")
    public ResponseEntity<MarkBasic> addMarkBasic(@RequestBody @Valid MarkBasicDTO mark) {

        return new ResponseEntity<>(markService.save(mark), HttpStatus.CREATED);

    }

}


