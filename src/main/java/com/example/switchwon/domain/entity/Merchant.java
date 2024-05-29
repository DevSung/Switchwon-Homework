package com.example.switchwon.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "merchant")
public class Merchant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx", nullable = false)
    private Long idx;

    /**
     * 상점 이름
     */
    @Column(name = "name", length = 50)
    private String name;

    /**
     * 수수료
     */
    @Column(name = "fee", precision = 5, scale = 2)
    private Double fee;

}
