package com.jmg.blog.domain.board.repository;

import com.jmg.blog.presentation.controller.board.response.BoardPostResponse;

import java.util.List;

public interface BoardPostRepositoryCustom {
    List<BoardPostResponse> findBoardPostListByBoardName(String boardName);
}
