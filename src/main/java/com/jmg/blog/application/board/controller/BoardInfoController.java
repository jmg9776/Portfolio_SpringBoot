package com.jmg.blog.application.board.controller;

import com.jmg.blog.application.board.response.BoardCategoryInfoResponse;
import com.jmg.blog.application.board.service.BoardInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class BoardInfoController {
    private final BoardInfoService boardInfoService;

    @GetMapping("/")
    public ResponseEntity<List<BoardCategoryInfoResponse>> getTest() {
        return ResponseEntity.ok().body(boardInfoService.findByCategory(null));
    }
}
