package com.zhonghai.drawingapp.repository;

import com.zhonghai.drawingapp.entity.Board;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends MongoRepository<Board, String> {
    // 根据 boardId 查询 Board 文档
    Optional<Board> findByBoardId(Integer boardId);
}
