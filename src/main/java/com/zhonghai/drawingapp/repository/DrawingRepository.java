package com.zhonghai.drawingapp.repository;

import com.zhonghai.drawingapp.entity.Drawing;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DrawingRepository extends MongoRepository<Drawing, String> {

    // 根据 boardId 查询 Drawing 文档
    List<Drawing> findByBoardIdAndEnable(Integer boardId, boolean enable);
}
