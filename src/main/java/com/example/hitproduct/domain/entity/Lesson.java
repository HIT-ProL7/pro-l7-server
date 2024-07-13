package com.example.hitproduct.domain.entity;
/*
 * @author HongAnh
 * @created 10 / 07 / 2024 - 7:51 AM
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

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "lessons")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String name;

    @CreationTimestamp
    Timestamp createAt;

    @UpdateTimestamp
    Timestamp updatedAt;

    @ManyToOne
    @JoinColumn(name = "classroom_id", nullable = false)
    Classroom classroom;
}
