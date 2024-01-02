package com.jmg.blog.board;

import com.jmg.blog.application.board.service.BoardPostService;
import com.jmg.blog.presentation.controller.board.response.BoardPostResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("dev")
@ExtendWith(SpringExtension.class)
public class BoardPostServiceTest {
    @Autowired
    private BoardPostService boardPostService;
    private static final Logger logger = LoggerFactory.getLogger(BoardPostServiceTest.class);
    @Test
    @DisplayName("게시글 목록 불러오기")
    void boardPostListTest() {
        // 면접 후기에 해당하는 게시글 목록을 검색합니다.
        List<BoardPostResponse> interviewReviews = boardPostService.findBoardPostListByBoardName("면접 후기", PageRequest.of(0, 10)).getContent();
        logger.info(interviewReviews.toString());
        // 결과가 null이 아닌지 확인합니다.
        assertNotNull(interviewReviews, "'면접 후기'에 해당하는 게시글 목록이 null입니다.");

        // 모든 게시글 목록을 검색합니다.
        List<BoardPostResponse> allPosts = boardPostService.findBoardPostListByBoardName(null, PageRequest.of(0, 10)).getContent();
        logger.info(allPosts.toString());
        // 결과가 null이 아닌지 확인합니다.
        assertNotNull(allPosts, "모든 게시글 목록이 null입니다.");
    }
}
