package com.devteam.mikufunbackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResourceDTO {
    @JsonProperty("Title")
    private String fileName;
    @JsonProperty("FileSize")
    private String size;
    @JsonProperty("Magnet")
    private String link;
}
