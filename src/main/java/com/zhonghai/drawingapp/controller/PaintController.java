package com.zhonghai.drawingapp.controller;

import com.zhonghai.drawingapp.entity.JoinEntity;
import com.zhonghai.drawingapp.resopnse.ApiResponse;
import com.zhonghai.drawingapp.resopnse.ApiStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

@RestController
@RequestMapping("/api/board/{boardId}")
public class PaintController {

    @PostMapping("/join")
    public ResponseEntity<ApiResponse> joinBoard(@PathVariable Integer boardId, @RequestBody JoinEntity joinEntity) {
        // 这里需要实现逻辑来判断画板是否存在，以及用户是否可以加入 todo
        boolean boardExists = null != boardId && boardId >= 100;

        if (boardExists) {
            String nickname = "";
            if (joinEntity.getNickname() == null || StringUtils.hasLength(joinEntity.getNickname())) {
                nickname = "User_" + UUID.randomUUID().toString().substring(0, 8); // 生成随机昵称
            }
            ApiResponse response = new ApiResponse(ApiStatus.SUCCESS.isSuccess(), ApiStatus.SUCCESS.getMessage(), nickname);
            return ResponseEntity.status(ApiStatus.SUCCESS.getStatusCode()).body(response);
        } else {
            ApiResponse response = new ApiResponse(ApiStatus.BOARD_NOT_FOUND.isSuccess(), ApiStatus.BOARD_NOT_FOUND.getMessage(), null);
            return ResponseEntity.status(ApiStatus.BOARD_NOT_FOUND.getStatusCode()).body(response);
        }
    }

    @GetMapping("/data")
    public ResponseEntity<String> getBoardData(@PathVariable String boardId) {
        // 这里应该有逻辑来获取画板数据
        String boardData = "{\"data\": \"example drawing data\"}"; // 假设数据
        return ResponseEntity.ok(boardData);
    }

    @PostMapping("/action")
    public ResponseEntity<String> postAction(@PathVariable String boardId, @RequestBody String actionData) {
        // 这里应该有逻辑来处理和验证绘画动作数据
        // 假设数据总是正确的
        return ResponseEntity.ok("绘画动作发送成功");
    }

    // WebSocket接口仍然按照之前的配置
    @MessageMapping("/draw")
    @SendTo("/topic/paint")
    public String handleDraw(String message) {
        System.out.println("message = " + message);
        return message; // 直接将接收到的绘画数据广播给所有订阅者
    }
}
