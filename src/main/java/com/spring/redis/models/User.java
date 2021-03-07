package com.spring.redis.models;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class User  implements Serializable{
    private int id;
    private String name;
    private String country;
    private int age;

}
