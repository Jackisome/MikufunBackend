package com.devteam.mikufunbackend.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @author Jackisome
 * @date 2021/10/6
 */
@Data
@Builder
public class ResourceResponseV0 {
    String resourceId;
    String resourceName;
}
