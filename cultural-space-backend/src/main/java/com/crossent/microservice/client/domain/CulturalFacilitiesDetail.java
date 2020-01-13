package com.crossent.microservice.client.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@ApiModel
@Entity
public class CulturalFacilitiesDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(value = "아이디")
    @JsonIgnore
    private long id;

    @ApiModelProperty(required = true, value = "문화 공간명")
    @Column
    private String name;

    @ApiModelProperty(required = true, value = "장르 분류 코드")
    @OneToOne
    @JoinColumn(name = "code")
    private FacilityType type;

    @ApiModelProperty(required = true, value = "전화번호")
    @Column
    private String phone;

    @ApiModelProperty(value = "홈페이지")
    @Column
    private String homepage;

    @ApiModelProperty(required = true, value = "주소")
    @Column
    private String address;

    @ApiModelProperty(value = "입장 요금")
    @Column
    private String enterFee;

    @ApiModelProperty(value = "무료 구분")
    @Column
    private String free;

    @ApiModelProperty( value = "X좌표")
    @Column
    private Float xCoord;

    @ApiModelProperty(value = "Y좌표")
    @Column
    private Float yCoord;

    @CreatedDate
    private Date created;

    public CulturalFacilitiesDetail() { }


    public CulturalFacilitiesDetail(String name, FacilityType type, String phone, String homepage, String address, String enterFee, Float xCoord, Float yCoord, String free) {
        this.name = name;
        this.type = type;
        this.phone = phone;
        this.homepage = homepage;
        this.address = address;
        this.enterFee = enterFee;
        this.free = free;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.created = new Date();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FacilityType getType() {
        return type;
    }

    public void setType(FacilityType type) {
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEnterFee() {
        return enterFee;
    }

    public void setEnterFee(String enterFee) {
        this.enterFee = enterFee;
    }

    public String getFree() {
        return free;
    }

    public void setFree(String free) {
        this.free = free;
    }

    public Float getxCoord() {
        return xCoord;
    }

    public void setxCoord(Float xCoord) {
        this.xCoord = xCoord;
    }

    public Float getyCoord() {
        return yCoord;
    }

    public void setyCoord(Float yCoord) {
        this.yCoord = yCoord;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
