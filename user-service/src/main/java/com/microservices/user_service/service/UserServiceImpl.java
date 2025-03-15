package com.microservices.user_service.service;

import com.microservices.user_service.commons.exceptions.CustomException;
import com.microservices.user_service.commons.response.BaseResponse;
import com.microservices.user_service.entity.User;
import com.microservices.user_service.model.request.AddressRequest;
import com.microservices.user_service.model.request.UserRequest;
import com.microservices.user_service.model.response.AddressResponse;
import com.microservices.user_service.model.response.UserResponse;
import com.microservices.user_service.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static com.microservices.user_service.commons.util.DateTimeUtil.getInstant;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final IUserRepository iUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final RestTemplate restTemplate;

    @Transactional
    @Override
    public BaseResponse createUser(UserRequest request) {
        if (!StringUtils.hasText(request.getFullName())) {
            throw new CustomException("Full name must not be null or empty");
        }
        if (!StringUtils.hasText(request.getEmail())) {
            throw new CustomException("Email must not be null or empty");
        }
        if (!StringUtils.hasText(request.getPassword())) {
            throw new CustomException("Password must not be null or empty");
        }
        if (!StringUtils.hasText(request.getMobileNumber())) {
            throw new CustomException("Mobile number must not be null or empty");
        }

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setMobileNumber(request.getMobileNumber());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setDeleted(false);
        user.setCreatedTime(getInstant());
        user.setModifiedTime(getInstant());
        User details = iUserRepository.save(user);

        /*String url = "http://localhost:6000/v1/address/create/" + details.getId();
        System.out.println(url);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json"); // Set headers if required

        HttpEntity<List<AddressRequest>> requestEntity = new HttpEntity<>(request.getAddress(), headers);

        ResponseEntity<BaseResponse> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                BaseResponse.class
        );*/

      /*  String url = "http://localhost:6000/v1/address/create/" + details.getId(); // Make sure Address Service is running

        ResponseEntity<AddressRequest> responseEntity = restTemplate.postForEntity(url,request.getAddress(), AddressRequest.class);
      */
        String url = "http://localhost:6000/v1/address/create/old/" + details.getId(); // Make sure Address Service is running

        ResponseEntity<AddressRequest> responseEntity = restTemplate.postForEntity(url,request.getAddress(), AddressRequest.class);

        return BaseResponse.builder()
                .status(true)
                .message("SUCCESS")
                .systemTime(getInstant())
                .build();
    }

    @Override
    public List<UserResponse> getUserDetails() {

        List<UserResponse> userResponses = new ArrayList<>();

        List<User> users = iUserRepository.findAll();

        if (!users.isEmpty()) {
            users.forEach(a -> {
                UserResponse userResponse = UserResponse.builder().build();
                BeanUtils.copyProperties(a, userResponse);
                String url = "http://localhost:6000/v1/address/details/" + a.getId();

                List<AddressResponse> addressResponses = restTemplate.getForObject(url, null, AddressResponse.class);

                userResponse.setAddress(addressResponses);
                userResponses.add(userResponse);
            });
        }

        return userResponses;
    }
}
