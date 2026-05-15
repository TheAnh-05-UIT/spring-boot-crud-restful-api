package com.java.springrestful.domain.dto;

import java.time.Instant;

import com.java.springrestful.util.constant.GenderEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseUpdateUserDTO {

    private Long id;
    private String name;
    private int age;
    private GenderEnum gender;
    private String address;
    private Instant updateAt;
    private String updateBy;
}
