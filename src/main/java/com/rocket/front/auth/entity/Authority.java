package com.rocket.front.auth.entity;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "authority")
public class Authority {
    @Id
    private Integer authoritySeq;
    private String name;

}
