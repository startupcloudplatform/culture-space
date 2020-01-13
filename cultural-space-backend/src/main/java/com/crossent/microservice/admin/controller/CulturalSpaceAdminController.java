package com.crossent.microservice.admin.controller;

import com.crossent.microservice.admin.service.CulturalSpaceAdminService;
import com.crossent.microservice.api.dto.CulturalFacilitiesDetailResponse;
import com.crossent.microservice.client.domain.CulturalFacilitiesDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping(value="/api/facilities")
@RestController
public class CulturalSpaceAdminController {

    @Autowired
    private CulturalSpaceAdminService culturalSpaceAdminService;

    @RequestMapping(value="/list", method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<CulturalFacilitiesDetailResponse> list(){
        return culturalSpaceAdminService.listData();
    }

    @RequestMapping(value="/update", method= RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void save(){
        culturalSpaceAdminService.saveData();
    }

    @RequestMapping(value="/delete", method=RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteAll(){
        culturalSpaceAdminService.delete();
    }

    @RequestMapping(value="/delete/{code}", method=RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteByCode(@PathVariable float code){
        culturalSpaceAdminService.deleteById(code);
    }


    @RequestMapping(value = "/upload/file", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public int uploadFile(@RequestParam(value="file") MultipartFile file) {
        return culturalSpaceAdminService.uploadFile(file);
    }
}
