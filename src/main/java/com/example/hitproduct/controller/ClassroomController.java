package com.example.hitproduct.controller;
/*
 * @author HongAnh
 * @created 23 / 06 / 2024 - 4:21 PM
 * @project pro-l7-server
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */


import com.example.hitproduct.constant.Endpoint;
import com.example.hitproduct.domain.dto.global.GlobalResponse;
import com.example.hitproduct.domain.dto.global.Meta;
import com.example.hitproduct.domain.dto.request.AddMemberRequest;
import com.example.hitproduct.domain.dto.request.CreateClassroomRequest;
import com.example.hitproduct.domain.dto.response.ClassroomResponse;
import com.example.hitproduct.service.ClassroomService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClassroomController {
    ClassroomService classroomService;

    @PostMapping(Endpoint.V1.Classroom.CREATE)
    public ResponseEntity<GlobalResponse<Meta, ClassroomResponse>> createClassroom(@RequestBody @Valid CreateClassroomRequest request){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(classroomService.createClass(request));
    }

    @PostMapping(Endpoint.V1.Classroom.ADD_MEMBER)
    public ResponseEntity<GlobalResponse<Meta, String>> addMember(@PathVariable(name = "classroomId") Long id,
                                                                  @RequestBody AddMemberRequest request,
                                                                  @AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(classroomService.addMember(id, request, userDetails.getUsername()));
    }
}
