package com.devteam.mikufunbackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResourceEpisodeDTO {

    @JsonProperty("episodeId")
    private Integer episodeId;

    @JsonProperty("episodeName")
    private String episodeName;

    @JsonProperty("airDate")
    private Date airDate;

}
