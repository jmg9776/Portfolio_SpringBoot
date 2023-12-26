package com.jmg.blog.application.board.service;

import com.jmg.blog.application.board.response.BoardCategoryInfoResponse;
import com.jmg.blog.domain.board.repository.BoardInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardInfoRepository boardInfoRepository;

    public List<BoardCategoryInfoResponse> getBoardList(String category) {
        return boardInfoRepository.findBoardInfoListByBoardCategory(category);
    }
}
