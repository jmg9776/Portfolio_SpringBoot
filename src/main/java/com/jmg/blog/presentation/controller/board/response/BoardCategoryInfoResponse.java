package com.jmg.blog.presentation.controller.board.response;

import java.util.List;

public record BoardCategoryInfoResponse(
        String categoryName,
        Long count,
        List<BoardInfoResponse> boardInfoResponse
) {
}
