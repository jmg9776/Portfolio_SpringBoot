package com.jmg.blog.presentation.controller.board.response;

import java.time.LocalDateTime;

public record BoardPostResponse(
        Long id,
        String title,
        Long view,
        LocalDateTime createAt,
        String boardName,
        String primaryImage,
        String content
) {
}
