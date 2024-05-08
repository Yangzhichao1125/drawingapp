package com.zhonghai.drawingapp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;


@NoArgsConstructor
@Data
@Document("drawings")
public class Drawing {
    @Id
    private String id;  // 将 MongoDB 的 _id 字段映射为 id
    private Boolean enable;
    private Integer boardId;
    private String creator; // 创建者
    private String updater; // 更新者
    private Date createdAt; // 创建时间
    private Date updateAt; // 更新时间
    private List<Action> actions;
    }