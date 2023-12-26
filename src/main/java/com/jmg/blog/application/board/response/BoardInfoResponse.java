package com.jmg.blog.application.board.response;

public record BoardInfoResponse(
        String boardName,
        Long postCount
) {
}
