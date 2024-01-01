package com.jmg.blog.presentation.controller.board;

import com.jmg.blog.application.board.service.BoardPostService;
import com.jmg.blog.presentation.controller.board.response.BoardPostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public/board/post")
public class BoardPostController {
    private final BoardPostService boardPostService;

    @GetMapping(value = {"/{boardName}", "/"})
    public ResponseEntity<List<BoardPostResponse>> getBoardInfoAllByCategory(@PathVariable(required = false) String boardName) {
        if (boardName.equals("All")) boardName = null;
        List<BoardPostResponse> boardCategoryInfoResponseList = boardPostService.findBoardPostListByBoardName(boardName);
        return ResponseEntity.ok().body(boardCategoryInfoResponseList);
    }
}
