package com.korean.koreanairDiet.domain.menu.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "common")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_common")
    private Long idCommon;

    @Column(name = "common_name", length = 100)
    private String commonName;

    @Column(name = "weekday", length = 10)
    private String weekday;
}