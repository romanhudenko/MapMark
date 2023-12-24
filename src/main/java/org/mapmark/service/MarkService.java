package org.mapmark.service;

import org.mapmark.dto.MarkDTO;
import org.mapmark.model.Mark;
import org.mapmark.repo.MarkRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MarkService {


    private final MarkRepository markRepository;

    public MarkService(MarkRepository markRepository) {
        this.markRepository = markRepository;
    }

    public List<Mark> get() {
        return markRepository.findAll();
    }

    public Mark get(String id) {
        return markRepository.findById(id).orElse(null);
    }


    public List<Mark> getByName(String Name) {
        return markRepository.findByNameContaining(Name);
    }

    public List<Mark> getMarksInGroup(Long groupId) {
        return markRepository.findMarksByGroupsId(groupId);
    }

    public void remove(String id) {
        markRepository.deleteById(id);
    }

    public Mark save(MarkDTO markDTO) {


        LocalDateTime timestamp = LocalDateTime.now();

        Mark mark = Mark.builder()
                .name(markDTO.getName())
                .latitude(markDTO.getLatitude())
                .longitude(markDTO.getLongitude())
                .userId(1)
                .colorHex(markDTO.getColorHex())
                .CreateTimestamp(timestamp)
                .UpdateTimestamp(timestamp)
                .build();

        return markRepository.save(mark);

    }


    public Mark update(String id, MarkDTO markDTO) {

        Mark mark = markRepository.findById(id).orElse(null);

        if (mark == null) {
            return null;
        }

        mark.setName(markDTO.getName());
        mark.setLatitude(markDTO.getLatitude());
        mark.setLongitude(markDTO.getLongitude());
        mark.setColorHex(markDTO.getColorHex());
        mark.setUpdateTimestamp(LocalDateTime.now());

        return markRepository.save(mark);

    }


}
