package com.jmg.blog.domain.board.model;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private BoardCategory boardCategory;
}
