package com.devteam.mikufunbackend.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @author Jackisome
 * @date 2021/10/22
 */
@Data
@Builder
public class LoginV0 {
    String token;
    boolean visitor;
}
