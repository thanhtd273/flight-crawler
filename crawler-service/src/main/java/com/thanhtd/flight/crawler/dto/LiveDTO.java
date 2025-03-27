package com.thanhtd.flight.crawler.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LiveDTO {
    private Date updated;

    private Float latitude;

    private Float longitude;

    private Float altitude;

    private Float direction;

    @JsonProperty("speed_horizontal")
    private Float speedHorizontal;

    @JsonProperty("speed_vertical")
    private Float speedVertical;

    @JsonProperty("is_ground")
    private Boolean isGround;
}
