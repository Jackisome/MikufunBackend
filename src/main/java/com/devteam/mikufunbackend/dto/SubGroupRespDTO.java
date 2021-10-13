package com.devteam.mikufunbackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SubGroupRespDTO {

    @JsonProperty("Subgroups")
    private List<SubGroupDTO> subGroups;
}
