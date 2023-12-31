package com.jmg.blog.presentation.controller.board.response;

import java.time.LocalDateTime;

public record BoardPostListResponse(
        Long id,
        String title,
        Long view,
        LocalDateTime createAt
) {
}
