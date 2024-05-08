package com.zhonghai.drawingapp.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhonghai.drawingapp.entity.*;
import com.zhonghai.drawingapp.repository.BoardRepository;
import com.zhonghai.drawingapp.repository.DrawingRepository;
import com.zhonghai.drawingapp.resopnse.ApiResponse;
import com.zhonghai.drawingapp.resopnse.ApiStatus;
import com.zhonghai.drawingapp.service.DrawingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class DrawingServiceImpl implements DrawingService {

    @Autowired
    DrawingRepository drawingRepository;
    @Autowired
    BoardRepository boardRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public ResponseEntity joinBoard(Integer boardId, JoinEntity joinEntity) {

        //查看是否有看板存在
        ResponseEntity<ApiResponse> boardNotFound = hasBoard(boardId);
        if (boardNotFound != null) return boardNotFound;

        // 没有昵称就生成随机昵称
        if (joinEntity.getNickname() == null || !StringUtils.hasLength(joinEntity.getNickname())) {
            String nickname = "User_" + UUID.randomUUID().toString().substring(0, 8);
            joinEntity.setNickname(nickname);
        }

        //返回前端
        ApiResponse response = new ApiResponse(ApiStatus.SUCCESS.getStatusCode(), ApiStatus.SUCCESS.isSuccess(), ApiStatus.SUCCESS.getMessage(), joinEntity);
        return ResponseEntity.status(ApiStatus.SUCCESS.getStatusCode()).body(response);
    }

    @Override
    public ResponseEntity getBoardData(Integer boardId) {
        //查看是否有看板存在
        ResponseEntity<ApiResponse> boardNotFound = hasBoard(boardId);
        if (boardNotFound != null) return boardNotFound;

        //根据看板id查询可用绘画数据
        List<Drawing> drawings = drawingRepository.findByBoardIdAndEnable(boardId, true);


        //返回前端
        ApiResponse response = new ApiResponse(ApiStatus.SUCCESS.getStatusCode(), ApiStatus.SUCCESS.isSuccess(), ApiStatus.SUCCESS.getMessage(), drawings);
        return ResponseEntity.status(ApiStatus.SUCCESS.getStatusCode()).body(response);
    }


    @Override
    public ResponseEntity<ApiResponse> action(Integer boardId, UserAction userAction) {

        //查看是否有看板存在
        ResponseEntity<ApiResponse> boardNotFound = hasBoard(boardId);
        if (boardNotFound != null) return boardNotFound;

        //参数错误判断
        if (userAction == null || userAction.getType() == null || !StringUtils.hasLength(userAction.getNickName())) {
            ApiResponse response = new ApiResponse(ApiStatus.BAD_REQUEST.getStatusCode(), ApiStatus.BAD_REQUEST.isSuccess(), ApiStatus.BAD_REQUEST.getMessage(), null);
            return ResponseEntity.status(ApiStatus.BOARD_NOT_FOUND.getStatusCode()).body(response);
        }

        //根据类型判断如何处理
        if (ActionType.DRAW.getCode().equals(userAction.getType())) {
            //绘画逻辑
            return drawAction(boardId, userAction);
        } else if (ActionType.UNDO.getCode().equals(userAction.getType())) {
            //撤销逻辑
            return undoAction(userAction);
        } else if (ActionType.REDO.getCode().equals(userAction.getType())) {
            //重做逻辑
            return redoAction(boardId, userAction);
        }
        return null;
    }

    private ResponseEntity<ApiResponse> redoAction(Integer boardId, UserAction userAction) {
        //数据持久化
        updateDrawingEnableByBoardId(boardId, false, userAction.getNickName());
        //前端根据type类型，清空画布即可
        ApiResponse response = new ApiResponse(ApiStatus.SUCCESS.getStatusCode(), ApiStatus.SUCCESS.isSuccess(), ApiStatus.SUCCESS.getMessage(), userAction);
        return ResponseEntity.status(ApiStatus.SUCCESS.getStatusCode()).body(response);
    }


    private ResponseEntity<ApiResponse> undoAction(UserAction userAction) {
        //数据持久化
        //根据id逻辑删除 drawing
        updateDrawingEnableById(userAction.getDrawingId(), false, userAction.getNickName());

        //前端根据id删除数据，重画即可
        ApiResponse response = new ApiResponse(ApiStatus.SUCCESS.getStatusCode(), ApiStatus.SUCCESS.isSuccess(), ApiStatus.SUCCESS.getMessage(), userAction);
        return ResponseEntity.status(ApiStatus.SUCCESS.getStatusCode()).body(response);
    }

    private ResponseEntity<ApiResponse> drawAction(Integer boardId, UserAction userAction) {
        log.info("boardId : " + boardId + " ; userAction : " + userAction.toString());
        //数据持久化
        //构造 drawing 对象
        Drawing drawing = new Drawing();
        drawing.setCreator(userAction.getNickName());
        drawing.setCreatedAt(new Date());
        drawing.setBoardId(boardId);
        drawing.setEnable(true);
        String message = userAction.getMessage();
        if (StringUtils.hasLength(message)) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Action>>() {
            }.getType();
            List<Action> actions = gson.fromJson(message, type);
            drawing.setActions(actions);
            log.info("数据转换成功");
        }
        Drawing result = drawingRepository.insert(drawing);

        //返回前端
        userAction.setDrawingId(result.getId());
        ApiResponse response = new ApiResponse(ApiStatus.SUCCESS.getStatusCode(), ApiStatus.SUCCESS.isSuccess(), ApiStatus.SUCCESS.getMessage(), userAction);
        return ResponseEntity.status(ApiStatus.SUCCESS.getStatusCode()).body(response);
    }

    //找不到画板就返回 BOARD_NOT_FOUND
    private ResponseEntity<ApiResponse> hasBoard(Integer boardId) {
        log.info("boardId : " + boardId);

        if (boardId == null || !boardRepository.findByBoardId(boardId).isPresent()) {
            ApiResponse response = new ApiResponse(ApiStatus.BOARD_NOT_FOUND.getStatusCode(), ApiStatus.BOARD_NOT_FOUND.isSuccess(), ApiStatus.BOARD_NOT_FOUND.getMessage(), null);
            return ResponseEntity.status(ApiStatus.BOARD_NOT_FOUND.getStatusCode()).body(response);
        }
        return null;
    }


    public void updateDrawingEnableById(String id, boolean enable, String nickname) {
        Query query = new Query(Criteria.where("_id").is(id));
        Update update = new Update();
        update.set("enable", enable);
        update.set("updater", nickname);
        update.set("updateAt", new Date());
        mongoTemplate.updateFirst(query, update, Drawing.class);
    }

    private void updateDrawingEnableByBoardId(Integer boardId, boolean enable, String nickname) {
        Query query = new Query(Criteria.where("boardId").is(boardId));
        Update update = new Update();
        update.set("enable", enable);
        update.set("updater", nickname);
        update.set("updateAt", new Date());
        mongoTemplate.updateMulti(query, update, Drawing.class);
    }

}
