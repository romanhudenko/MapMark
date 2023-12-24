package org.mapmark.model;


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
@Table(name = "marks")
public class Mark {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private double latitude;
    private double longitude;
    private int userId;
    private String colorHex;

    @Basic(optional = false)
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private LocalDateTime CreateTimestamp;

    @Basic(optional = false)
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private LocalDateTime UpdateTimestamp;

    @Builder.Default
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name="mark_group",
            joinColumns=  @JoinColumn(name="mark_id", referencedColumnName="id"),
            inverseJoinColumns= @JoinColumn(name="group_id", referencedColumnName="id") )
    private Set<Group> groups = new HashSet<>();


    public void addGroup(Group group) {
        this.groups.add(group);
        group.getMarks().add(this);
    }

    public void removeGroup(Long groupId) {
        Group group = this.groups.stream().filter(g -> g.getId() == groupId).findFirst().orElse(null);
        if (group != null){
            this.groups.remove(group);
            group.getMarks().remove(this);
        }
    }

}
