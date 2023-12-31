package com.jmg.blog.presentation.controller.board.response;

import java.util.List;

public record BoardCategoryInfoAllResponse(
        long count,
        List<BoardCategoryInfoResponse> boardCategoryInfoResponseList
) {
}
