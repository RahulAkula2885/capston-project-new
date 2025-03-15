package com.microservices.user_service.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "fullName")
    private String fullName;

    @Column(name = "email")
    private String email;

    @Column(name = "mobileNumber")
    private String mobileNumber;

    @Column(name = "password")
    private String password;

    @Column(name = "deleted")
    private boolean deleted;

    @Column(name = "createdTime")
    private Instant createdTime;

    @Column(name = "modifiedTime")
    private Instant modifiedTime;

}
