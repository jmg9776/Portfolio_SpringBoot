package com.jmg.blog.board;

import com.jmg.blog.presentation.controller.board.response.BoardInfoResponse;
import com.jmg.blog.domain.board.repository.BoardInfoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("dev")
public class BoardInfoRepositoryTest {

    @Qualifier("boardInfoRepositoryImpl")
    @Autowired
    private BoardInfoRepository boardInfoRepository;
    private static final Logger logger = LoggerFactory.getLogger(BoardInfoRepositoryTest.class);

    @Test
    @DisplayName("보드의 정보 가져오기")
    void boardListTest() {
        String greenText = "블로그";
        List<BoardInfoResponse> red = boardInfoRepository.findBoardInfo("NO CONTENT");
        List<BoardInfoResponse> green = boardInfoRepository.findBoardInfo(greenText);
        List<BoardInfoResponse> all = boardInfoRepository.findBoardInfo(null);

        logger.info(red.toString());
        logger.info(green.toString());
        logger.info(all.toString());

        // 없는 컨텐츠 - red 리스트가 비어있음을 확인
        assertTrue(red.isEmpty(), "리스트가 비어있지 않습니다. 'NO CONTENT'로 검색했을 때 결과가 없어야 합니다.");

        // green 리스트에 특정 요소가 포함되어 있는지 확인
        assertTrue(green.stream().anyMatch(board -> greenText.equals(board.boardName())),
                "리스트에 '" + greenText + "'가 포함되어 있지 않습니다. '프로젝트'로 검색했을 때 해당 이름을 가진 보드가 최소 하나 이상 있어야 합니다.");
    }

    @Test
    @DisplayName("카테고리 기준으로 보드 정보 가져오기")
    void boardListByCategory() {
        logger.info(boardInfoRepository.findBoardInfoByBoardCategory(null).toString());
    }
}
