package com.crossent.microservice.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CulturalFacilitiesRowResponse<T> {

    @JsonProperty("row")
    private List<CulturalFacilitiesDetailResponse> detail;

    public CulturalFacilitiesRowResponse(){}

    public List<CulturalFacilitiesDetailResponse> getDetail() {
        return detail;
    }

    public void setDetail(List<CulturalFacilitiesDetailResponse> detail) {
        this.detail = detail;
    }
}
