package com.rocket.front.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class Member {
    @Id
    private Long memberSeq;
    private String email;
    private String password;
    private String name;
    private String profileImg;


    @ManyToOne
    @JoinColumn(name = "authority_seq")
    private Authority authority;

}
