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
@Document("boards")
public class Board {
    @Id
    private String id;  // MongoDB的_id字段，这里使用String类型，因为MongoDB默认的ID类型是ObjectId
    private Integer boardId;  // 画板ID
    private String name;    // 图名
    private String creator; // 创建者
    private String updater; // 更新者
    private Date createdAt; // 创建时间
    private Date updateAt; // 更新时间
}
