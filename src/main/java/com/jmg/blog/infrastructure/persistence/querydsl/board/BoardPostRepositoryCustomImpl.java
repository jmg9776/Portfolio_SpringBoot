package com.jmg.blog.infrastructure.persistence.querydsl.board;

import com.jmg.blog.domain.board.model.QBoard;
import com.jmg.blog.domain.board.model.QBoardPost;
import com.jmg.blog.domain.board.repository.BoardPostRepositoryCustom;
import com.jmg.blog.presentation.controller.board.response.BoardPostResponse;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

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
    public PageImpl<BoardPostResponse> findBoardPostListByBoardName(String boardName, Pageable pageable) {
        long totalPosts = countBoardPostsByBoardName(boardName);
        long totalPages = (totalPosts + pageable.getPageSize() - 1) / pageable.getPageSize();
        List<BoardPostResponse> posts =
                jpaQueryFactory
                        .query()
                        .select(getBoardPostList())
                        .from(boardPost)
                        .innerJoin(board)
                        .on(boardPost.board.id.eq(board.id))
                        .where(createBoardNameCondition(boardName))
                        .orderBy(boardPost.createAt.desc())
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch();
        return new PageImpl<>(posts, pageable, totalPosts);
    }

    public long countBoardPostsByBoardName(String boardName) {
        return Optional.ofNullable(
                        jpaQueryFactory
                                .select(boardPost.count())
                                .from(boardPost)
                                .innerJoin(board)
                                .on(boardPost.board.id.eq(board.id))
                                .where(createBoardNameCondition(boardName))
                                .fetchOne())
                .orElse(1L);
    }

    // 게시물 목록을 생성하기 위한 프로젝션을 정의합니다.
    public ConstructorExpression<BoardPostResponse> getBoardPostList() {
        return Projections.constructor(
                BoardPostResponse.class,
                boardPost.id,
                boardPost.title,
                boardPost.view,
                boardPost.createAt,
                board.name,
                boardPost.primaryImage,
                boardPost.content.substring(0, 30)
        );
    }

    // 게시판 이름에 대한 조건을 생성하는 메서드입니다.
    public BooleanBuilder createBoardNameCondition(String boardName) {
        BooleanBuilder builder = new BooleanBuilder();
        if (boardName != null) {
            builder.and(board.name.eq(boardName));
        }
        return builder;
    }
}
