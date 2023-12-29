package com.jmg.blog.application.board.service;

import com.jmg.blog.application.board.response.BoardCategoryInfoResponse;
import com.jmg.blog.application.board.response.BoardInfoResponse;
import com.jmg.blog.domain.board.repository.BoardInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardInfoService {
    private final BoardInfoRepository boardInfoRepository;

    @Cacheable(value = "boardCategoryInfo", key = "#category")
    public List<BoardCategoryInfoResponse> findByCategory(String category) {
        return boardInfoRepository.findBoardInfoListByBoardCategory(category);
    }

    @Cacheable(value = "boardInfo", key = "#boardName")
    public List<BoardInfoResponse> findByBoardName(String boardName) {
        return boardInfoRepository.findBoardInfo(boardName);
    }
}