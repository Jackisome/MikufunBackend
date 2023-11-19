package com.devteam.mikufunbackend.entity;

import lombok.Data;

import java.util.List;

/**
 * @author Jackisome
 * @date 2021/10/4
 */
@Data
public class DandanPlayMatchResponseVO {
    boolean isMatched;
    List<DandanPlayMatchVO> matches;
    int errorCode;
    boolean success;
    String errorMessage;
}
