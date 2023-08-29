package com.rocket.front.entity;

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
