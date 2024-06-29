package com.example.hitproduct.service;
/*
 * @author HongAnh
 * @created 23 / 06 / 2024 - 4:13 PM
 * @project pro-l7-server
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.example.hitproduct.domain.dto.global.BlankData;
import com.example.hitproduct.domain.dto.global.GlobalResponse;
import com.example.hitproduct.domain.dto.global.Meta;
import com.example.hitproduct.domain.dto.request.CreateClassroomRequest;
import com.example.hitproduct.domain.dto.response.ClassroomResponse;
import com.example.hitproduct.domain.dto.response.UserResponse;

public interface ClassroomService {
    GlobalResponse<Meta, ClassroomResponse> createClass(CreateClassroomRequest request);

    GlobalResponse<Meta, Void> closeClassroom(String userId, Integer classroomId);
}
