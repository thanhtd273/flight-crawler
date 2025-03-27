package com.thanhtd.flight.crawler.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CrawlResult {

    private String apiSource;

    private String category;

    private Integer total;

    private Integer numberOfRequest;
}
