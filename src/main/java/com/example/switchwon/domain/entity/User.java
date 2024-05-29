package com.example.switchwon.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx", nullable = false)
    private Long idx;

    /**
     * 사용자 아이디
     */
    @Column(name = "user_id", length = 50, nullable = false, unique = true)
    private String userId;

    /**
     * 사용자 이름
     */
    @Column(name = "name", length = 50, nullable = false)
    private String name;

}
