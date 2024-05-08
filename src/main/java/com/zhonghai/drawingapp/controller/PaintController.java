package com.zhonghai.drawingapp.controller;

import com.zhonghai.drawingapp.entity.Drawing;
import com.zhonghai.drawingapp.entity.JoinEntity;
import com.zhonghai.drawingapp.entity.UserAction;
import com.zhonghai.drawingapp.resopnse.ApiResponse;
import com.zhonghai.drawingapp.service.DrawingService;
import org.springframework.beans.factory.annotation.Autowired;
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

    // WebSocket接口仍然按照之前的配置
    @MessageMapping("/draw")
    @SendTo("/topic/paint")
    public String handleDraw(String message) {
        System.out.println("message = " + message);
        return message; // 直接将接收到的绘画数据广播给所有订阅者
    }
}
