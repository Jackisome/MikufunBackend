package com.devteam.mikufunbackend.entity;

import lombok.Data;

/**
 * @author Jackisome
 * @date 2021/10/4
 */
@Data
public class MatchV0 {
    int episodeId;
    int animeId;
    String animeTitle;
    String episodeTitle;
    String type;
    String typeDescription;
    int shift;
}
