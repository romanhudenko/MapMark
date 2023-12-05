package org.mapmark.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class MarkBasic {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String Id;
    private String name;
    private double latitude;
    private double longitude;
    private int userId;
    private String colorHex;
    private int groupId;

    @Basic(optional = false)
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private LocalDateTime timestamp;

}
