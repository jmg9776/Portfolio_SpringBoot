package com.jmg.blog.presentation.controller.board;

import com.jmg.blog.presentation.controller.board.response.BoardCategoryInfoAllResponse;
import com.jmg.blog.presentation.controller.board.response.BoardCategoryInfoResponse;
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

    @GetMapping(value = {"/category/{categoryName}"})
    public ResponseEntity<List<BoardCategoryInfoResponse>> getBoardInfoByCategory(@PathVariable(required = false) String categoryName) {
        return ResponseEntity.ok().body(boardInfoService.findByCategory(categoryName));
    }

    @GetMapping(value = {"/category"})
    public ResponseEntity<BoardCategoryInfoAllResponse> getBoardInfoAllByCategory() {
        List<BoardCategoryInfoResponse> boardCategoryInfoResponseList = boardInfoService.findByCategory(null);

        long sum = boardCategoryInfoResponseList.stream()
                .mapToLong(BoardCategoryInfoResponse::count)
                .sum();

        return ResponseEntity.ok().body(new BoardCategoryInfoAllResponse(sum, boardCategoryInfoResponseList));
    }
}
