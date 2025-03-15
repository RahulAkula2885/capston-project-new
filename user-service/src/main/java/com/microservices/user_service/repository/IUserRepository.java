package com.microservices.user_service.repository;

import com.microservices.user_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User,Long> {
}
