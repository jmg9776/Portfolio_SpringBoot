package com.jmg.blog.application.board.service;

import com.jmg.blog.domain.board.repository.BoardInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardInfoRepository boardInfoRepository;
    public void getBoardList(String category) {
        boardInfoRepository.findBoardInfoListByBoardCategory(category);
    }
}
