package com.devteam.mikufunbackend.entity;

import lombok.Data;

import java.util.List;

/**
 * @author Jackisome
 * @date 2021/10/2
 */
@Data
public class Aria2ResponseV0 {
    String id;
    String jsonrpc;
    List<Aria2StatusV0> result;
}
