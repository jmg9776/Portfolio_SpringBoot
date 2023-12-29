package com.jmg.blog.application.board.response;

import java.io.Serializable;

public record BoardInfoResponse(
        String boardName,
        Long postCount
) implements Serializable {
}
