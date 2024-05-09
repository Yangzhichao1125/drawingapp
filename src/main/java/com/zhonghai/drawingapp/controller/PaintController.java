package com.zhonghai.drawingapp.controller;

import com.zhonghai.drawingapp.entity.Drawing;
import com.zhonghai.drawingapp.entity.JoinEntity;
import com.zhonghai.drawingapp.entity.UserAction;
import com.zhonghai.drawingapp.resopnse.ApiResponse;
import com.zhonghai.drawingapp.service.DrawingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/board/{boardId}")
public class PaintController {

    @Autowired
    DrawingService drawingService;

    @PostMapping("/join")
    public ResponseEntity<ApiResponse> joinBoard(@PathVariable Integer boardId, @RequestBody JoinEntity joinEntity) {
        return drawingService.joinBoard(boardId,joinEntity);
    }

    @GetMapping("/data")
    public ResponseEntity<ApiResponse> getBoardData(@PathVariable Integer boardId) {
        return drawingService.getBoardData(boardId);
    }

    @PostMapping("/action")
    public ResponseEntity<ApiResponse> action(@PathVariable Integer boardId, @RequestBody UserAction userAction) {
        return drawingService.action(boardId,userAction);
    }


    @MessageMapping("/api/board/{boardId}/action")
    @SendTo("/topic/api/board/{boardId}/paint")
    public ResponseEntity<ApiResponse> handleBoardAction(@DestinationVariable("boardId") Integer boardId, @RequestBody UserAction userAction) {
        System.out.println("userAction = " + userAction.toString());
        // 业务逻辑处理
        return drawingService.action(boardId,userAction);
    }

//    @MessageMapping("/api/board/{boardId}/action")
//    @SendTo("/topic/api/board/{boardId}/paint")
//    public String handleBoardAction(@DestinationVariable("boardId") Integer boardId, @RequestBody UserAction userAction) {
//        System.out.println("userAction = " + userAction.toString());
//        // 业务逻辑处理
//        return userAction.getMessage();
//    }
}
