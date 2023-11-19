package com.devteam.mikufunbackend.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Jackisome
 * @date 2021/9/27
 */

@Data
//开启setter方法的链式调用
@Accessors(chain = true)
//无参构造方法
@NoArgsConstructor
public class Aria2RequestVO {
    /**
     * id随机生成，也可以手动设置
     */
    private String id = UUID.randomUUID().toString();
    private String jsonrpc = "2.0";
    private String method;
    private String url;
    private List<Object> params = new ArrayList<>();

    /**
     * 添加下载参数
     * @return
     */
    public Aria2RequestVO addParam(Object obj) {
        params.add(obj);
        return this;
    }
}
