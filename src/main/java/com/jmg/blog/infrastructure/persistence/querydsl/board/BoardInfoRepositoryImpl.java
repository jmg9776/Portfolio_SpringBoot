package com.jmg.blog.infrastructure.persistence.querydsl.board;

import com.jmg.blog.presentation.controller.board.response.BoardCategoryInfoResponse;
import com.jmg.blog.presentation.controller.board.response.BoardInfoResponse;
import com.jmg.blog.domain.board.model.QBoard;
import com.jmg.blog.domain.board.model.QBoardCategory;
import com.jmg.blog.domain.board.model.QBoardPost;
import com.jmg.blog.domain.board.repository.BoardInfoRepository;
import com.jmg.blog.infrastructure.mapper.BoardInfoMapper;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.querydsl.core.types.ExpressionUtils.count;

@Repository
@RequiredArgsConstructor
public class BoardInfoRepositoryImpl implements BoardInfoRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final QBoard board = QBoard.board;
    private final QBoardPost boardPost = QBoardPost.boardPost;
    private final QBoardCategory boardCategory = QBoardCategory.boardCategory;
    private final BoardInfoMapper boardInfoMapper;

    /**
     * 특정 보드의 이름을 기반으로 해당 보드의 게시글 수와 이름을 조회합니다.
     * 전달된 boardName이 null일 경우, 시스템에 존재하는 모든 보드의 게시글 수와 이름을 반환합니다.
     *
     * @param boardName 검색하고자 하는 보드의 이름. null일 경우 모든 보드 정보를 반환합니다.
     * @return 각 보드의 이름과 게시글 수를 포함하는 BoardInfoResponse 객체의 리스트.
     *         리스트는 각 보드의 이름과 해당 보드에 속한 게시글 수를 포함합니다.
     *         보드가 존재하지 않거나 게시글이 없는 경우, 빈 리스트가 반환될 수 있습니다.
     */
    @Override
    public List<BoardInfoResponse> findBoardInfo(String boardName) {
        return jpaQueryFactory
                .query()
                .select(getBoardInfoResponse())
                .from(board)
                .where(creatBoardNameCondition(boardName))
                .fetch();
    }

    /**
     * 주어진 보드 이름에 따라 Querydsl의 BooleanBuilder 조건을 생성합니다.
     * 이 조건은 보드 이름에 따른 조회를 제한하는 데 사용됩니다.
     *
     * @param boardName 필터링할 보드 이름. null이면 모든 보드를 대상으로 합니다.
     * @return 생성된 BooleanBuilder 조건.
     */
    private BooleanBuilder creatBoardNameCondition(String boardName) {
        BooleanBuilder builder = new BooleanBuilder();
        if (boardName != null) {
            builder.and(board.name.eq(boardName));
        }
        return builder;
    }

    /**
     * BoardInfoResponse 객체를 생성하기 위한 ConstructorExpression을 반환합니다.
     * 이 표현식은 보드의 이름과 해당 보드에 속한 게시글 수를 포함합니다.
     *
     * @return 보드 정보를 담는 BoardInfoResponse 객체를 생성하기 위한 ConstructorExpression.
     */
    private ConstructorExpression<BoardInfoResponse> getBoardInfoResponse() {
        return Projections.constructor(
                BoardInfoResponse.class,
                board.name.as("boardName"),
                postCountSubQuery()
        );
    }

    /**
     * 특정 보드에 속한 게시글의 총 수를 계산하는 서브쿼리를 나타내는 Expression을 반환합니다.
     * 이 서브쿼리는 각 보드의 게시글 수를 집계합니다.
     *
     * @return 특정 보드의 게시글 수를 계산하는 Expression.
     */
    private Expression<Long> postCountSubQuery() {
        return ExpressionUtils.as(JPAExpressions.select(count(boardPost.id))
                        .from(boardPost)
                        .where(boardPost.board.eq(board)),
                "postCount");
    }

    /**
     * 주어진 카테고리 이름에 따라 보드 정보를 조회하고, 각 카테고리별로 보드 게시글 수와 함께 정보를 반환합니다.
     *
     * @param categoryName 조회할 보드 카테고리 이름. null이거나 빈 문자열일 경우, 모든 카테고리를 조회합니다.
     * @return 각 카테고리별로 분류된 보드 정보와 게시글 수를 포함하는 BoardCategoryInfoResponse 객체의 리스트.
     */
    @Override
    public List<BoardCategoryInfoResponse> findBoardInfoByBoardCategory(String categoryName) {
        Map<String, List<BoardInfoResponse>> boardInfoMap = getBoardInfoMap(categoryName);

        return boardInfoMap.entrySet().stream()
                .map(entry -> new BoardCategoryInfoResponse(entry.getKey(),
                        getPostCountSum(entry.getValue()),
                        entry.getValue()))
                .collect(Collectors.toList());
    }

    /**
     * 주어진 BoardInfoResponse 리스트에서 모든 게시글의 총 수를 계산합니다.
     *
     * @param boardInfoResponses 게시글 수를 계산할 BoardInfoResponse 객체 리스트.
     * @return 주어진 리스트에 포함된 모든 게시글의 총 수.
     */
    private Long getPostCountSum(List<BoardInfoResponse> boardInfoResponses) {
        return boardInfoResponses.stream()
                .mapToLong(BoardInfoResponse::postCount)
                .sum();
    }

    /**
     * 주어진 카테고리 이름에 따라 데이터베이스에서 보드 정보를 조회하고,
     * 카테고리 이름을 키로 하고 BoardInfoResponse 리스트를 값으로 하는 맵을 반환합니다.
     *
     * @param categoryName 조회할 카테고리 이름.
     * @return 카테고리 이름을 키로, 해당 카테고리의 보드 정보 리스트를 값으로 하는 맵.
     */
    private Map<String, List<BoardInfoResponse>> getBoardInfoMap(String categoryName) {
        List<Tuple> results = findBoardInfoTupleListByCategory(categoryName);

        return results.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(
                        tuple -> Optional.ofNullable(tuple.get(boardCategory.name)).orElse("Unknown"),
                        Collectors.mapping(
                                boardInfoMapper::toBoardInfoResponse,
                                Collectors.toList()
                        )
                ));
    }

    /**
     * 주어진 카테고리 이름을 사용하여 데이터베이스에서 보드 정보를 조회합니다.
     * 조회는 boardCategory, board, boardPost 테이블을 조인하여 수행합니다.
     *
     * @param categoryName 조회할 카테고리 이름. null이거나 빈 문자열일 경우, 모든 카테고리를 조회합니다.
     * @return 조회된 결과를 Tuple 리스트로 반환합니다.
     */
    private List<Tuple> findBoardInfoTupleListByCategory(String categoryName) {
        return jpaQueryFactory
                .select(boardCategory.name, board.name, count(boardPost.id))
                .from(boardCategory)
                .leftJoin(board).on(board.boardCategory.eq(boardCategory))
                .leftJoin(boardPost).on(boardPost.board.eq(board))
                .groupBy(boardCategory.name, board.name, boardCategory.id)
                .where(createBoardCategoryNameCondition(categoryName))
                .orderBy(boardCategory.id.desc())
                .fetch();
    }

    /**
     * 주어진 카테고리 이름을 바탕으로 Querydsl의 BooleanBuilder 조건을 생성합니다.
     * 이 조건은 카테고리 이름에 따른 조회를 제한하는 데 사용됩니다.
     *
     * @param categoryName 조회할 카테고리 이름. null일 경우 조건이 적용되지 않습니다.
     * @return 생성된 BooleanBuilder 조건.
     */
    private BooleanBuilder createBoardCategoryNameCondition(String categoryName) {
        BooleanBuilder builder = new BooleanBuilder();
        if (categoryName != null) {
            builder.and(boardCategory.name.eq(categoryName));
        }
        return builder;
    }
}
