package com.jmg.blog.infrastructure.mapper;

import com.jmg.blog.presentation.controller.board.response.BoardInfoResponse;
import com.querydsl.core.Tuple;
import org.mapstruct.Mapper;

import java.util.Optional;

import static com.jmg.blog.domain.board.model.QBoard.board;
import static com.jmg.blog.domain.board.model.QBoardPost.boardPost;
import static com.querydsl.core.types.ExpressionUtils.count;

@Mapper(componentModel = "spring")
public interface BoardInfoMapper {
    default BoardInfoResponse toBoardInfoResponse(Tuple tuple) {
        String boardName = Optional.ofNullable(tuple.get(board.name)).orElse("No Name");
        Long postCount = Optional.ofNullable(tuple.get(count(boardPost.id))).orElse(0L);
        return new BoardInfoResponse(boardName, postCount);
    }
}