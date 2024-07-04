package com.example.hitproduct.repository;
/*
 * @author HongAnh
 * @created 25 / 06 / 2024 - 8:13 PM
 * @project pro-l7-server
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.example.hitproduct.domain.entity.Classroom;
import com.example.hitproduct.domain.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {
    List<Position> findAllByClassroom(Classroom classroom);

    @Query("SELECT p.classroom FROM Position p WHERE p.user.id = :userId AND p.classroom.status = :isActive")
    List<Classroom> findActiveClassroomsByUserId(@Param("userId") String userId, @Param("isActive") boolean isActive);
}
