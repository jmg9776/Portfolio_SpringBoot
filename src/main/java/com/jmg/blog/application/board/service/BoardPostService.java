package com.jmg.blog.application.board.service;

import com.jmg.blog.domain.board.repository.BoardPostRepositoryCustom;
import com.jmg.blog.presentation.controller.board.response.BoardPostResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardPostService {
    private final BoardPostRepositoryCustom boardPostRepositoryCustom;

    @Transactional
    public PageImpl<BoardPostResponse> findBoardPostListByBoardName(String boardName, Pageable pageable) {
        return boardPostRepositoryCustom.findBoardPostListByBoardName(boardName, pageable);
    }
}
