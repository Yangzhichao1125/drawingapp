package com.zhonghai.drawingapp.service;

import com.zhonghai.drawingapp.entity.Drawing;
import com.zhonghai.drawingapp.entity.JoinEntity;
import com.zhonghai.drawingapp.entity.UserAction;
import com.zhonghai.drawingapp.resopnse.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface DrawingService {

    ResponseEntity<ApiResponse> joinBoard( Integer boardId, JoinEntity joinEntity);

    ResponseEntity<ApiResponse> getBoardData(Integer boardId);

    ResponseEntity<ApiResponse> action(Integer boardId, UserAction userAction);
}
