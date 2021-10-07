package com.devteam.mikufunbackend.entity;

import lombok.Data;

import java.util.List;

/**
 * @author Jackisome
 * @date 2021/10/4
 */
@Data
public class DandanPlayMatchResponseV0 {
    boolean isMatched;
    List<DandanPlayMatchV0> matches;
    int errorCode;
    boolean success;
    String errorMessage;
}
