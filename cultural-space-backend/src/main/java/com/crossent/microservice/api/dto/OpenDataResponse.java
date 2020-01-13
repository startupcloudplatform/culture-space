package com.crossent.microservice.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenDataResponse<T> {

    @JsonProperty("SearchCulturalFacilitiesDetailService")
    private T row;

    public OpenDataResponse(){}

    public T getRow() {
        return row;
    }

    public void setRow(T row) {
        this.row = row;
    }
}
