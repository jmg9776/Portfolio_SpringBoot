package com.jmg.blog.board;

import com.jmg.blog.presentation.controller.board.response.BoardCategoryInfoResponse;
import com.jmg.blog.presentation.controller.board.response.BoardInfoResponse;
import com.jmg.blog.application.board.service.BoardInfoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("dev")
public class BoardInfoServiceTest {

    @Autowired
    private BoardInfoService boardInfoService;
    private static final Logger logger = LoggerFactory.getLogger(BoardInfoServiceTest.class);

    @Test
    @DisplayName("보드의 정보 가져오기")
    void boardInfoTest() {
        String searchText = "블로그";
        List<BoardInfoResponse> noContentBoards = boardInfoService.findByBoardName("NO CONTENT");
        List<BoardInfoResponse> matchedBoards = boardInfoService.findByBoardName(searchText);
        List<BoardInfoResponse> allBoards = boardInfoService.findByBoardName(null);

        logger.info(noContentBoards.toString());
        logger.info(matchedBoards.toString());
        logger.info(allBoards.toString());

        // 없는 컨텐츠 - noContentBoards 리스트가 비어있음을 확인
        assertTrue(noContentBoards.isEmpty(), "리스트가 비어있지 않습니다. 'NO CONTENT'로 검색했을 때 결과가 없어야 합니다.");

        // matchedBoards 리스트에 특정 요소가 포함되어 있는지 확인
        assertTrue(matchedBoards.stream().anyMatch(board -> searchText.equals(board.boardName())),
                "리스트에 '" + searchText + "'가 포함되어 있지 않습니다. '프로젝트'로 검색했을 때 해당 이름을 가진 보드가 최소 하나 이상 있어야 합니다.");
    }

    @Test
    @DisplayName("카테고리 기준으로 보드 정보 가져오기")
    void boardInfoByCategoryTest() {
        // 테스트에 사용할 데이터 설정
        String validCategory = "취업 준비";
        String invalidCategory = "존재하지않는카테고리";

        // 올바른 카테고리로 검색
        List<BoardCategoryInfoResponse> validResults = boardInfoService.findByCategory(validCategory);
        logger.info("올바른 카테고리로 검색한 결과: " + validResults.toString());
        assertFalse(validResults.isEmpty(), "올바른 카테고리로 검색했을 때 결과가 비어있으면 안 됩니다.");

        // 잘못된 카테고리로 검색
        List<BoardCategoryInfoResponse> invalidResults = boardInfoService.findByCategory(invalidCategory);
        logger.info("잘못된 카테고리로 검색한 결과: " + invalidResults.toString());
        assertTrue(invalidResults.isEmpty(), "잘못된 카테고리로 검색했을 때 결과가 비어있어야 합니다.");

        // null로 검색
        List<BoardCategoryInfoResponse> nullResults = boardInfoService.findByCategory(null);
        logger.info("카테고리 없이 검색한 결과: " + nullResults.toString());
        assertNotNull(nullResults, "카테고리 없이 검색한 결과는 null이 아니어야 합니다.");
    }

}
