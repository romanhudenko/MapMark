package org.mapmark.service;

import org.mapmark.dto.MarkBasicDTO;
import org.mapmark.model.MarkBasic;
import org.mapmark.repo.MarkRepository;
import org.mapmark.util.GlobalExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class MarkService {



    private final MarkRepository markRepository;

    public MarkService(MarkRepository markRepository) {
        this.markRepository = markRepository;
    }

    public Iterable<MarkBasic> get() {
        return markRepository.findAll();
    }

    public MarkBasic get(String id) {
        return markRepository.findById(id).orElse(null);
    }

    public void remove(String id) {
        markRepository.deleteById(id);
    }

    public MarkBasic save(MarkBasicDTO markBasic) {


        MarkBasic mark = MarkBasic.builder()
                .name(markBasic.getName())
                .latitude(markBasic.getLatitude())
                .longitude(markBasic.getLongitude())
                .userId(1)
                .groupId(markBasic.getGroupId())
                .colorHex(markBasic.getColorHex())
                .timestamp(LocalDateTime.now()).build();

        return markRepository.save(mark);

    }
}
