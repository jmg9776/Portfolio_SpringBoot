package com.jmg.blog.domain.board.repository;

import com.jmg.blog.presentation.controller.board.response.BoardPostResponse;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public interface BoardPostRepositoryCustom {

    PageImpl<BoardPostResponse> findBoardPostListByBoardName(String boardName, Pageable pageable);
}
