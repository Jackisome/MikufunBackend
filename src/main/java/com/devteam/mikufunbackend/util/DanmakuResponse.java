package com.devteam.mikufunbackend.util;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author Jackisome
 * @date 2021/10/16
 */
@Data
@Builder
public class DanmakuResponse {
    int code;
    List<List<Object>> data;
}
