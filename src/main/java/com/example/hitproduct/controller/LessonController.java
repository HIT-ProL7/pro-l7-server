package com.example.hitproduct.controller;
/*
 * @author HongAnh
 * @created 13 / 07 / 2024 - 5:00 PM
 * @project pro-l7-server
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.example.hitproduct.constant.Endpoint;
import com.example.hitproduct.domain.dto.global.GlobalResponse;
import com.example.hitproduct.domain.dto.global.Meta;
import com.example.hitproduct.domain.dto.request.CreateLessonRequest;
import com.example.hitproduct.domain.dto.response.LessonResponse;
import com.example.hitproduct.service.LessonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
public class LessonController {
    LessonService lessonService;

    @Operation(summary = "Create a new lesson in classroom")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Create lesson successfully"),
            @ApiResponse(responseCode = "400", description = "Not found classroom"),
            @ApiResponse(responseCode = "403", description = "You not have permits")
    })
    @PostMapping(Endpoint.V1.Lesson.PREFIX)
    public ResponseEntity<GlobalResponse<Meta, LessonResponse>> createLesson(@RequestBody CreateLessonRequest request,
                                                                             @AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(lessonService.createLesson(request, userDetails.getUsername()));
    }
}
