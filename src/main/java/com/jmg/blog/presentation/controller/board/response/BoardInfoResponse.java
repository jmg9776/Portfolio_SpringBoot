package com.jmg.blog.presentation.controller.board.response;

public record BoardInfoResponse(
        String boardName,
        Long postCount
) {
}
