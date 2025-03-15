package com.microservices.user_service.controller;

import com.microservices.user_service.commons.exceptions.CustomException;
import com.microservices.user_service.commons.exceptions.GlobalExceptionHandling;
import com.microservices.user_service.commons.response.BaseResponse;
import com.microservices.user_service.model.request.UserRequest;
import com.microservices.user_service.model.response.UserResponse;
import com.microservices.user_service.service.IUserService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequestMapping(path = "/v1/user")
@RequiredArgsConstructor
@Tag(name = "User Service APIs")
public class UserController {

    private final GlobalExceptionHandling globalExceptionHandling;
    private final IUserService iUserService;

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<BaseResponse> createCustomException(CustomException ex) {
        return globalExceptionHandling.createBaseResponse(INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @Operation(
            description = "Create User",
            summary = "Create User",
            responses = {
                    @ApiResponse(
                            description = "SUCCESS",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "INTERNAL SERVER ERROR",
                            responseCode = "500"
                    )
            }
    )
    @PostMapping(value = "/create")
    public BaseResponse createUser(@RequestBody UserRequest request) throws IOException {
        return iUserService.createUser(request);
    }

    @Operation(
            description = "Get User Details",
            summary = "Get User Details",
            responses = {
                    @ApiResponse(
                            description = "SUCCESS",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "INTERNAL SERVER ERROR",
                            responseCode = "500"
                    )
            }
    )
    @GetMapping("/details")
    public List<UserResponse> getUserDetails() throws IOException {
        return iUserService.getUserDetails();
    }

}
