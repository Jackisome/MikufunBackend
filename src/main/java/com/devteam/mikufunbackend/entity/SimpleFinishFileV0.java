package com.devteam.mikufunbackend.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @author Jackisome
 * @date 2021/10/2
 */
@Data
@Builder
public class SimpleFinishFileV0 {
    int fileId;
    String fileName;
    boolean delete;
}
