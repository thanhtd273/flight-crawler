package com.thanhtd.flight.crawler.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Pagination {

    private Integer limit;

    private Integer offset;

    private Integer count;

    private Long total;
}
