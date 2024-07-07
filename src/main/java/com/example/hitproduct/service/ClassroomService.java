package com.example.hitproduct.service;
/*
 * @author HongAnh
 * @created 23 / 06 / 2024 - 4:13 PM
 * @project pro-l7-server
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.example.hitproduct.domain.dto.global.GlobalResponse;
import com.example.hitproduct.domain.dto.global.Meta;
import com.example.hitproduct.domain.dto.request.AddMemberRequest;
import com.example.hitproduct.domain.dto.request.CreateClassroomRequest;
import com.example.hitproduct.domain.dto.response.GetClassroomResponse;
import com.example.hitproduct.domain.dto.response.UserResponse;
import com.example.hitproduct.domain.entity.User;
import com.example.hitproduct.domain.dto.response.CreateClassroomResponse;
import com.example.hitproduct.domain.dto.response.GetClassroomResponse;

import java.util.List;

public interface ClassroomService {
    GlobalResponse<Meta, CreateClassroomResponse> createClass(CreateClassroomRequest request);

    GlobalResponse<Meta, GetClassroomResponse> getMembersOfClassroom(User currentUser, Integer classroomId);

    GlobalResponse<Meta, String> addMember(Integer classroomId, AddMemberRequest request, String studentCode);

    GlobalResponse<Meta, GetClassroomResponse> getClassroom(Integer id);

    GlobalResponse<Meta, List<GetClassroomResponse>> getMyClassroom(String studentCode);

    GlobalResponse<Meta, List<GetClassroomResponse>> getClassrooms();

    GlobalResponse<Meta, String> deleteMember(String username, Integer classId, String userId);
}
