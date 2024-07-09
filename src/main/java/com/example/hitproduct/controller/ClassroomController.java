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
import com.example.hitproduct.domain.dto.response.CreateClassroomResponse;
import com.example.hitproduct.domain.dto.response.GetClassroomResponse;
import com.example.hitproduct.domain.entity.User;
import com.example.hitproduct.service.ClassroomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClassroomController {
    ClassroomService classroomService;

    @Operation(summary = "Create a new classroom")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Classroom created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request format or data provided"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - user not authenticated")
    })
    @PostMapping(Endpoint.V1.Classroom.CREATE)
    public ResponseEntity<GlobalResponse<Meta, CreateClassroomResponse>> createClassroom(@RequestBody @Valid CreateClassroomRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(classroomService.createClass(request));
    }

    @Operation(summary = "Add a member to a classroom")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Member added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request format or data provided"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - user not authenticated")
    })
    @PostMapping(Endpoint.V1.Classroom.ADD_MEMBER)
    public ResponseEntity<GlobalResponse<Meta, String>> addMember(@PathVariable(name = "classroomId") Integer id,
                                                                  @RequestBody AddMemberRequest request,
                                                                  @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(classroomService.addMember(id, request, userDetails.getUsername()));
    }

    @Operation(summary = "Get details of a classroom by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved classroom details"),
            @ApiResponse(responseCode = "404", description = "Classroom not found")
    })
    @GetMapping(Endpoint.V1.Classroom.CLASSROOM_ID)
    public ResponseEntity<GlobalResponse<Meta, GetClassroomResponse>> getClassroom(@PathVariable(name = "classroomId") Integer id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(classroomService.getClassroom(id));
    }

    @Operation(summary = "Get all classrooms")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of classrooms"),
            @ApiResponse(responseCode = "404", description = "No classrooms found")
    })
    @GetMapping(Endpoint.V1.Classroom.PREFIX)
    public ResponseEntity<GlobalResponse<Meta, List<GetClassroomResponse>>> getClassrooms() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(classroomService.getClassrooms());
    }

    @Operation(summary = "Get classrooms that the current user is a member of")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved classrooms"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - user not authenticated")
    })
    @GetMapping(Endpoint.V1.Classroom.MY_CLASS)
    public ResponseEntity<GlobalResponse<Meta, List<GetClassroomResponse>>> getMyClassroom(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(classroomService.getMyClassroom(userDetails.getUsername()));
    }

    @Operation(summary = "Get members of a specific classroom")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved members of classroom"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - user not authenticated"),
            @ApiResponse(responseCode = "403", description = "Forbidden - user not authorized to access this classroom")
    })
    @GetMapping(Endpoint.V1.Classroom.GET_MEMBERS)
    public ResponseEntity<GlobalResponse<Meta, GetClassroomResponse>> getMembersOfClassroom(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Integer classroomId
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(classroomService.getMembersOfClassroom(currentUser, classroomId));
    }

    @Operation(summary = "Edit a member role in a classroom")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Member edit role successfully"),
            @ApiResponse(responseCode = "400", description = "Member or classroom not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - user not authenticated")
    })
    @PutMapping(Endpoint.V1.Classroom.EDIT_MEMBER_ROLE)
    public GlobalResponse<Meta, String> editMemberRole(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable(name = "classroomId") Integer classroomId,
            @PathVariable(name = "userId") String userId,
            @RequestParam(required = false, defaultValue = "MEMBER") String newRole
    ) {
        return classroomService.editMemberRole(userDetails.getUsername(), classroomId, userId, newRole);
    }

    @Operation(summary = "Delete a member from a classroom")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Member deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - user not authenticated"),
            @ApiResponse(responseCode = "403", description = "Forbidden - user not authorized to delete members from this classroom"),
            @ApiResponse(responseCode = "404", description = "Member not found in classroom")
    })
    @DeleteMapping(Endpoint.V1.Classroom.DELETE_MEMBER)
    public ResponseEntity<GlobalResponse<Meta, String>> deleteMemberFromClassroom(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable(name = "classroomId") Integer classId,
            @PathVariable(name = "userId") String userId
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(classroomService.deleteMember(userDetails.getUsername(), classId, userId));
    }
}
