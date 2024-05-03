package org.mapmark.service;

import lombok.RequiredArgsConstructor;
import org.mapmark.dto.MarkDTO;
import org.mapmark.model.Mark;
import org.mapmark.model.User;
import org.mapmark.repo.MarkRepository;
import org.mapmark.repo.UserRepository;
import org.mapmark.security.config.AuthFacadeImpl;
import org.mapmark.security.service.UserDetailsImpl;
import org.mapmark.util.exceptions.DataNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MarkService {

    private final AuthFacadeImpl authFacade;
    private final MarkRepository markRepository;


    public List<Mark> getMarks() {
        return markRepository.findByUser_Username(authFacade.getUsername());
    }

    public Mark getMarkByUUID(String id) {
        Mark mark = markRepository.findByIdAndUser_Username(id, authFacade.getUsername());
        if (mark == null) {
            throw new DataNotFoundException("Mark with id " + id + " not found");
        }
        return mark;
    }


    public List<Mark> getByName(String Name) {
        return markRepository.findByNameContainingAndUser_Username(Name, authFacade.getUsername());
    }


    public List<Mark> getMarksInGroup(Long groupId) {
        return markRepository.findMarksByGroupsIdAndUser_Username(groupId, authFacade.getUsername());
    }


    public void remove(String id) {
        if (markRepository.findByIdAndUser_Username(id, authFacade.getUsername()) != null) {
            markRepository.deleteById(id);
        }
    }


    public Mark save(MarkDTO markDTO) {

        LocalDateTime timestamp = LocalDateTime.now();

        Mark mark = Mark.builder()
                .name(markDTO.getName())
                .latitude(markDTO.getLatitude())
                .longitude(markDTO.getLongitude())
                .user(authFacade.getAuthenticatedUserEntity())
                .colorHex(markDTO.getColorHex())
                .CreateTimestamp(timestamp)
                .UpdateTimestamp(timestamp)
                .build();

        return markRepository.save(mark);

    }


    public Mark update(String id, MarkDTO markDTO) {

        Mark mark = markRepository.findByIdAndUser_Username(id, authFacade.getUsername());

        if (mark == null) {
            throw new DataNotFoundException("Mark with id " + id + " not found");
        }
        if (!markDTO.getName().isBlank()) {
            mark.setName(markDTO.getName());
        }
        if (markDTO.getLatitude() != 0 && mark.getLatitude() != markDTO.getLatitude()) {
            mark.setLatitude(markDTO.getLatitude());
        }
        if (markDTO.getLongitude() != 0 && mark.getLongitude() != markDTO.getLongitude()) {
            mark.setLongitude(markDTO.getLongitude());
        }
        if (!markDTO.getColorHex().isBlank()) {
            mark.setColorHex(markDTO.getColorHex());
        }

        mark.setUpdateTimestamp(LocalDateTime.now());

        return markRepository.save(mark);

    }


}
