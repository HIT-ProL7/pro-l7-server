package com.example.hitproduct.domain.entity;
/*
 * @author HongAnh
 * @created 21 / 06 / 2024 - 11:09 AM
 * @project pro-l7-server
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "classrooms")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Classroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String name;

    String description;

    String roadmap;

    @CreationTimestamp
    Timestamp createAt;

    @UpdateTimestamp
    Timestamp updatedAt;

    Timestamp startedDate;

    @Column(nullable = false)
    boolean status;

    @OneToMany(mappedBy = "classroom", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Position> positions;
}
