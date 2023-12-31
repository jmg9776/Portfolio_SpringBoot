package com.jmg.blog.domain.board.repository;

import com.jmg.blog.presentation.controller.board.response.BoardCategoryInfoResponse;
import com.jmg.blog.presentation.controller.board.response.BoardInfoResponse;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardInfoRepository {
    /**
     * 특정 보드의 이름을 기반으로 해당 보드의 게시글 수와 이름을 조회합니다.
     * 전달된 boardName이 null일 경우, 시스템에 존재하는 모든 보드의 게시글 수와 이름을 반환합니다.
     *
     * @param boardName 검색하고자 하는 보드의 이름. null일 경우 모든 보드 정보를 반환합니다.
     * @return 각 보드의 이름과 게시글 수를 포함하는 BoardInfoResponse 객체의 리스트.
     *         리스트는 각 보드의 이름과 해당 보드에 속한 게시글 수를 포함합니다.
     *         보드가 존재하지 않거나 게시글이 없는 경우, 빈 리스트가 반환될 수 있습니다.
     */
    List<BoardInfoResponse> findBoardInfo(String boardName);

    /**
     * 주어진 카테고리 이름에 따라 보드 정보를 조회하고, 각 카테고리별로 보드 게시글 수와 함께 정보를 반환합니다.
     *
     * @param categoryName 조회할 보드 카테고리 이름. null이거나 빈 문자열일 경우, 모든 카테고리를 조회합니다.
     * @return 각 카테고리별로 분류된 보드 정보와 게시글 수를 포함하는 BoardCategoryInfoResponse 객체의 리스트.
     */
    List<BoardCategoryInfoResponse> findBoardInfoByBoardCategory(String categoryName);
}
