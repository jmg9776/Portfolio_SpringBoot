package com.jmg.blog.domain.board;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardCategoryRepository extends JpaRepository<Board, BoardCategory> {
}
