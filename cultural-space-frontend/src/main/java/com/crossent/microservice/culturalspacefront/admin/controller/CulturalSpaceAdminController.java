package com.crossent.microservice.culturalspacefront.admin.controller;


import com.crossent.microservice.culturalspacefront.admin.domain.CulturalFacilitiesDetailResponse;
import com.crossent.microservice.culturalspacefront.admin.service.CulturalSpaceAdminService;
import com.crossent.microservice.culturalspacefront.portal.controller.SearchController;
import com.crossent.microservice.culturalspacefront.portal.domain.CulturalFacilitiesDetail;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RefreshScope
@RequestMapping(value="/api/facilities")
public class CulturalSpaceAdminController {

    private static final Logger logger = LoggerFactory.getLogger(SearchController.class);

    @Autowired
    private CulturalSpaceAdminService culturalSpaceAdminService;

    @ApiOperation(value = "전체 문화 공간 데이터 조회 API")
    @RequestMapping(value="/list", method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public List<CulturalFacilitiesDetail> list(){
        return culturalSpaceAdminService.listData();
    }

    @ApiOperation(value = "OpenAPI를 이용한 문화 공간 데이터 업데이트 API")
    @RequestMapping(value="/update", method= RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public void save(){
        culturalSpaceAdminService.saveData();
    }

    @ApiOperation(value = "Excel 파일을 이용한 문화 공간 데이터 업데이트 API")
    @RequestMapping(value="/file/upload", method=RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public int uploadFile(@RequestParam(value="file") MultipartFile file) {
        return culturalSpaceAdminService.uploadFile(file);
    }
}