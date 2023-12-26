package com.jmg.blog.domain.board.repository;

import com.jmg.blog.domain.board.model.Board;
import com.jmg.blog.domain.board.model.BoardCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardCategoryRepository extends JpaRepository<Board, BoardCategory> {
}
