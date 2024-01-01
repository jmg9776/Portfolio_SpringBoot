package com.jmg.blog.domain.board.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
public class BoardPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(length = 15_000)
    private String content;
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createAt;
    private Long view;
    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;
    @Column(length = 500)
    private String primaryImage;
}
