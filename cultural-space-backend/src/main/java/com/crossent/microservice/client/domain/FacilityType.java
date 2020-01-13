package com.crossent.microservice.client.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@ApiModel
@Entity
public class FacilityType {

    @Id
    @ApiModelProperty(required = true, value="장르 분류 코드")
    private float code;

    @Column
    @ApiModelProperty(required = true, value="장르 분류 명")
    private String name;

    public FacilityType(){};

    public FacilityType(float code, String name){
        this.code = code;
        this.name = name;
    }

    public float getCode() {
        return code;
    }

    public void setCode(float code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
