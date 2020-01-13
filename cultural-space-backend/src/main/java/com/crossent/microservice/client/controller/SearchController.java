package com.crossent.microservice.client.controller;

import com.crossent.microservice.client.domain.CulturalFacilitiesDetail;
import com.crossent.microservice.client.domain.FacilityType;
import com.crossent.microservice.client.dto.FreeDTO;
import com.crossent.microservice.client.service.SearchService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value="/api/search")
@RestController
@RefreshScope
public class SearchController {

    @Autowired
    private SearchService searchService;

    @ApiOperation(value = "문화 공간 데이터 전체 조회 API")
    @RequestMapping(value="/all", method=RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<CulturalFacilitiesDetail> searchAll(){
        return searchService.listAll();
    }

    @ApiOperation(value = "문화 공간 데이터 전체 개수 API")
    @RequestMapping(value="/all/count", method=RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public long countAllCulturalSpace(){
        return searchService.getCountsSpaceAll();
    }

    @ApiOperation(value = "키워드를 통한 문화 공간 데이터 조회 API")
    @RequestMapping(value="/list", method=RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<CulturalFacilitiesDetail> listByKeyword(@RequestParam(name="query", required = false)String keyword){
        return searchService.listByKeyword(keyword);
    }

    @RequestMapping(value="/name/list", method=RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<CulturalFacilitiesDetail> listByName(@RequestParam(name="query")String name){
        return searchService.listByName(name);
    }

    @RequestMapping(value="/type/list", method=RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<CulturalFacilitiesDetail> listByTypeName(@RequestParam(name="query")String type){
        return searchService.listByFacilityTypeName(type);
    }

    @RequestMapping(value="/type/list/{code}", method=RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<CulturalFacilitiesDetail> listByTypeCode(@PathVariable Float code){
        return searchService.listByFacilityTypeCode(code);
    }

    @RequestMapping(value="/address/list", method=RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<CulturalFacilitiesDetail> listByAddress(@RequestParam(name="query") String address){
        return searchService.listByAddress(address);
    }

    @RequestMapping(value="/fee/list", method=RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<CulturalFacilitiesDetail> listByFee(@RequestParam(name="query") String free){
        return searchService.listByFee(free);
    }

    @RequestMapping(value="/condition", method=RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<CulturalFacilitiesDetail> listWithCondition(@RequestParam(name="type", required = false) Float code,
                                                            @RequestParam(name="free", required = false) String free,
                                                            @RequestParam(name="address", required = false) String address){
        return searchService.listWithCondition(code, free, address);
    }

    @RequestMapping(value="/type/group", method=RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<FacilityType> listGroupByType(){
        return searchService.listGroupByType();
    }

    @RequestMapping(value="/fee/group", method=RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<FreeDTO> listGroupByFree(){
        return searchService.listGroupByFree();
    }

}
