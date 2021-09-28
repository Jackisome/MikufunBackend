package com.devteam.mikufunbackend.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Jackisome
 * @date 2021/9/27
 */
@Data
@Accessors(chain = true)
public class Aria2OptionEntity {
    String dir;
//    String out;
    String refer;
}
