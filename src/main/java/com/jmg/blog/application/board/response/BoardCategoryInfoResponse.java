package com.jmg.blog.application.board.response;

import java.util.List;

public record BoardCategoryInfoResponse(
        String categoryName,
        Long count,
        List<BoardInfoResponse> boardInfoResponse
) {
}
