package com.zhonghai.drawingapp.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

//@Controller
public class DrawingController {
//    @MessageMapping("/draw")  // 定义消息的地址，对应客户端发送消息的地址
//    @SendTo("/topic/paint")  // 定义消息广播的地址，所有订阅此地址的客户端都会接收到消息
//    public String handleDraw(String message) {
//        // 这个方法接收来自客户端的消息（绘画数据），然后将其广播到订阅了"/topic/paint"的客户端
//        return message;  // 直接将接收到的消息返回，进行广播
//    }
}