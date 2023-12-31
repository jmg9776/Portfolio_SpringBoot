package com.jmg.blog.application.board.service;

import com.jmg.blog.presentation.controller.board.response.BoardCategoryInfoResponse;
import com.jmg.blog.presentation.controller.board.response.BoardInfoResponse;
import com.jmg.blog.domain.board.repository.BoardInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardInfoService {
    private final BoardInfoRepository boardInfoRepository;

    /**
     * 주어진 카테고리에 따라 보드 정보를 조회하고 캐시합니다.
     * 캐시된 데이터는 'boardCategoryInfo' 캐시에 저장되며, 카테고리 이름이 키로 사용됩니다.
     *
     * @param category 조회할 보드의 카테고리 이름. null이면 모든 카테고리의 보드를 조회합니다.
     * @return 주어진 카테고리에 해당하는 보드 정보의 리스트를 반환합니다.
     * 각 정보에는 카테고리별로 분류된 보드의 이름과 게시글 수가 포함됩니다.
     */
    @Cacheable(value = "boardCategoryInfo", key = "#category != null ? #category : 'all'")
    public List<BoardCategoryInfoResponse> findByCategory(String category) {
        return boardInfoRepository.findBoardInfoByBoardCategory(category);
    }

    /**
     * 주어진 보드 이름에 따라 보드 정보를 조회하고 캐시합니다.
     * 캐시된 데이터는 'boardInfo' 캐시에 저장되며, 보드 이름이 키로 사용됩니다.
     *
     * @param boardName 조회할 보드의 이름. null이면 시스템에 존재하는 모든 보드의 정보를 반환합니다.
     * @return 주어진 이름의 보드 정보 리스트를 반환합니다.
     * 각 정보에는 보드의 이름과 해당 보드에 속한 게시글 수가 포함됩니다.
     */
    @Cacheable(value = "boardInfo", key = "#boardName != null ? #boardName : 'all'")
    public List<BoardInfoResponse> findByBoardName(String boardName) {
        return boardInfoRepository.findBoardInfo(boardName);
    }
}
