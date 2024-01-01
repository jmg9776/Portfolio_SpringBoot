package com.jmg.blog.application.board.service;

import com.jmg.blog.domain.board.repository.BoardPostRepositoryCustom;
import com.jmg.blog.presentation.controller.board.response.BoardPostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardPostService {
    private final BoardPostRepositoryCustom boardPostRepositoryCustom;

    public List<BoardPostResponse> findBoardPostListByBoardName(String boardName) {
        return boardPostRepositoryCustom.findBoardPostListByBoardName(boardName);
    }
}
