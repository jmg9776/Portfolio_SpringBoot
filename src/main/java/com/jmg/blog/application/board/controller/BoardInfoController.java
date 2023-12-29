package com.jmg.blog.application.board.controller;

import com.jmg.blog.application.board.response.BoardCategoryInfoResponse;
import com.jmg.blog.application.board.service.BoardInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/public/board/info")
public class BoardInfoController {
    private final BoardInfoService boardInfoService;

    @GetMapping(value = {"/category/{categoryName}", "/category"})
    public ResponseEntity<List<BoardCategoryInfoResponse>> getBoardInfoByCategory(@PathVariable(required = false) String categoryName) {
        return ResponseEntity.ok().body(boardInfoService.findByCategory(categoryName));
    }
}
