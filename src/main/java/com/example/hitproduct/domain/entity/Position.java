package com.example.hitproduct.domain.entity;

/*
 * @author HongAnh
 * @created 21 / 06 / 2024 - 6:47 PM
 * @project pro-l7-server
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "positions")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "classroom_id", nullable = false)
    Classroom classroom;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    SeatRole seatRole;
}
