package com.devteam.mikufunbackend.entity;

import lombok.Data;

/**
 * @author Jackisome
 * @date 2021/11/14
 */
@Data
public class Aria2SingleResponseVO {
    String id;
    String jsonrpc;
    Aria2StatusVO result;
}
