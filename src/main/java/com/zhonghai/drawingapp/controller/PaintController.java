package com.zhonghai.drawingapp.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/board/{boardId}")
public class PaintController {

    @PostMapping("/join")
    public ResponseEntity<String> joinBoard(@PathVariable String boardId, @RequestBody String nickname) {
        // 这里需要实现逻辑来判断画板是否存在，以及用户是否可以加入
        boolean boardExists = true; // 假设这里有逻辑来检查画板是否存在
        if (boardExists) {
            // 这里可以添加用户到画板的逻辑
            return ResponseEntity.ok("成功加入画板");
        } else {
            return ResponseEntity.status(404).body("画板不存在或无法加入");
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
        return message; // 直接将接收到的绘画数据广播给所有订阅者
    }
}
