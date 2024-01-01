package com.jmg.blog.domain.board.repository;

import com.jmg.blog.domain.board.model.BoardPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardPostRepository extends JpaRepository<BoardPost, Long> {
}
