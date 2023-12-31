package com.jmg.blog.infrastructure.persistence.querydsl.board;

import com.jmg.blog.domain.board.model.QBoard;
import com.jmg.blog.domain.board.model.QBoardPost;
import com.jmg.blog.domain.board.repository.BoardPostRepositoryCustom;
import com.jmg.blog.presentation.controller.board.response.BoardPostListResponse;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardPostRepositoryCustomImpl implements BoardPostRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
    private final QBoard board = QBoard.board;
    private final QBoardPost boardPost = QBoardPost.boardPost;

    /**
     * 게시판 이름을 기반으로 게시물 목록을 검색하는 메서드입니다.
     *
     * @param boardName 검색할 게시판 이름. null일 경우 전체 목록을 반환 합니다.
     * @return 검색된 게시물 목록
     */
    @Override
    public List<BoardPostListResponse> findBoardPostListByBoardName(String boardName) {
        return jpaQueryFactory
                .query()
                .select(getBoardPostList())
                .from(boardPost)
                .innerJoin(board)
                .on(boardPost.board.id.eq(board.id))
                .where(createBoardNameCondition(boardName))
                .fetch();
    }

    // 게시물 목록을 생성하기 위한 프로젝션을 정의합니다.
    private ConstructorExpression<BoardPostListResponse> getBoardPostList() {
        return Projections.constructor(
                BoardPostListResponse.class,
                boardPost.id,
                boardPost.title,
                boardPost.view,
                boardPost.createAt
        );
    }

    // 게시판 이름에 대한 조건을 생성하는 메서드입니다.
    private BooleanBuilder createBoardNameCondition(String boardName) {
        BooleanBuilder builder = new BooleanBuilder();
        if (boardName != null) {
            builder.and(board.name.eq(boardName));
        }
        return builder;
    }
}
