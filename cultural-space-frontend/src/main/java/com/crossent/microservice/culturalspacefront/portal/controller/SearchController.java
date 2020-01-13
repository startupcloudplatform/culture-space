package com.crossent.microservice.culturalspacefront.portal.controller;

import com.crossent.microservice.culturalspacefront.portal.domain.CulturalFacilitiesDetail;
import com.crossent.microservice.culturalspacefront.portal.domain.FacilityType;
import com.crossent.microservice.culturalspacefront.portal.dto.FreeDTO;
import com.crossent.microservice.culturalspacefront.portal.service.SearchService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RefreshScope
@RequestMapping(value="/api/search")
public class SearchController {

    private static final Logger logger = LoggerFactory.getLogger(SearchController.class);

    @Autowired
    private SearchService searchService;

    @ApiOperation(value = "키워드를 통한 문화 공간 데이터 조회 API")
    @RequestMapping(value="/list", method=RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('USER')")
    public List<CulturalFacilitiesDetail> listByKeyword(@RequestParam(name="query", required = false)String keyword){
        return searchService.listByKeyword(keyword);
    }

    @ApiOperation(value = "주소 검색을 통한 문화 공간 데이터 조회 API")
    @RequestMapping(value="/address/list", method=RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('USER')")
    public List<CulturalFacilitiesDetail> listByAddress(@RequestParam(name="query") String address){
        return searchService.listByAddress(address);
    }

    @ApiOperation(value = "종류,위치,요금을 통한 문화 공간 데이터 조회 API")
    @RequestMapping(value="/condition", method=RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('USER')")
    public List<CulturalFacilitiesDetail> listWithCondition(@RequestParam(name="type", required = false) Float code,
                                                            @RequestParam(name="free", required = false ) String free,
                                                            @RequestParam(name="address", required = false) String address){
        return searchService.listWithCondition(code, free, address);
    }

    @ApiOperation(value = "문화 공간 시설 분류명 그룹 조회 API")
    @RequestMapping(value="/type/group", method=RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('USER')")
    public List<FacilityType> listGroupByType(){

        return searchService.listGroupByType();
    }

    @ApiOperation(value = "문화 공간 입장 요금 그룹 조회 API")
    @RequestMapping(value="/fee/group", method=RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('USER')")
    public List<FreeDTO> listGroupByFree(){

        return searchService.listGroupByFree();
    }

}
