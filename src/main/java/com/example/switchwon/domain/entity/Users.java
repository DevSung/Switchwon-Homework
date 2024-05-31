package com.example.switchwon.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
public class Users extends BaseEntity {

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

    /**
     * 잔액 정보
     */
    @Setter
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    private List<AccountBalance> accountBalances;
}
