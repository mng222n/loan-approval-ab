package com.cdd.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;

public class PageResult<T> {
    public PageResult(Collection<T> records, long totalRecords, long totalPages){
        this.records = records;
        this.totalRecords = totalRecords;
        this.totalPages = totalPages;
    }

    private Collection<T> records;
    @JsonProperty("total_records")
    private long totalRecords;
    @JsonProperty("total_pages")
    private long totalPages;

    public Collection<T> getRecords() {
        return records;
    }

    public void setRecords(Collection<T> records) {
        this.records = records;
    }

    public long getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(long totalRecords) {
        this.totalRecords = totalRecords;
    }

    public long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }
}
