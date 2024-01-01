package org.mapmark.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "groups")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    private String description;

    //todo default icon + think about format
    private byte[] icon;

    @Basic(optional = false)
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private LocalDateTime CreateTimestamp;

    @Basic(optional = false)
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private LocalDateTime UpdateTimestamp;

    @Builder.Default
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            mappedBy = "groups")
    @JsonIgnore
    private Set<Mark> marks = new HashSet<>();

}
