package com.microservices.user_service.service;

import com.microservices.user_service.commons.response.BaseResponse;
import com.microservices.user_service.model.request.UserRequest;
import com.microservices.user_service.model.response.UserResponse;

import java.util.List;

public interface IUserService {
    BaseResponse createUser(UserRequest request);

    List<UserResponse> getUserDetails();
}
