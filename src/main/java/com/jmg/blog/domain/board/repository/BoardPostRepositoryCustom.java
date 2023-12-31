package com.jmg.blog.domain.board.repository;

import com.jmg.blog.presentation.controller.board.response.BoardPostListResponse;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardPostRepositoryCustom {
    List<BoardPostListResponse> findBoardPostListByBoardName(String boardName);
}
