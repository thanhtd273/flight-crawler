package com.thanhtd.flight.crawler.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class AviationHttpResponse<T> {
    private Pagination pagination;

    private List<T> data;
}
