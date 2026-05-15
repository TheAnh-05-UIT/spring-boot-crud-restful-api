package com.java.springrestful.domain.dto;

import java.time.Instant;

import com.java.springrestful.util.constant.GenderEnum;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserResultDTO {

    private Long id;
    private String name;
    private String email;
    private int age;
    private GenderEnum gender;
    private String address;
    private Instant createAt;
    private String createBy;
}
