package com.zhonghai.drawingapp.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Action {

//    @JsonProperty("x0")
    private Integer x0;
//    @JsonProperty("y0")
    private Integer y0;
//    @JsonProperty("x1")
    private Integer x1;
//    @JsonProperty("y1")
    private Integer y1;
//    @JsonProperty("color")
    private String color;
//    @JsonProperty("size")
    private String size;
}
