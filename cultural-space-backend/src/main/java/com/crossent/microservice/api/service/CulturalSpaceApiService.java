package com.crossent.microservice.api.service;

import com.crossent.microservice.api.dto.CulturalFacilitiesDetailResponse;
import com.crossent.microservice.api.dto.CulturalFacilitiesRowResponse;
import com.crossent.microservice.api.dto.OpenDataResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import okhttp3.HttpUrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Service
@RefreshScope
public class CulturalSpaceApiService {
    private static final Logger logger = LoggerFactory.getLogger(CulturalSpaceApiService.class);
    @Value("${openData.market.api:}")
    private String openDataApi;

    @Value("${openData.market.authKey:}")
    private String authKey;

    @Autowired
    private RequestService requestService;

    public List<CulturalFacilitiesDetailResponse> listCulturalSpaceData(){
        OpenDataResponse<CulturalFacilitiesRowResponse> contents = null;

        try{
            HttpUrl.Builder urlBuilder = HttpUrl.parse(openDataApi).newBuilder();
            urlBuilder.addQueryParameter("auth_key", authKey);
            String url = urlBuilder.build().toString();
            String result  = requestService.getHttp(url);

            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            StringReader reader = new StringReader(result);
            contents  = mapper.readValue(reader, new TypeReference<OpenDataResponse<CulturalFacilitiesRowResponse>>() {});
            logger.debug(contents.toString());

        }catch (Exception e){
            e.printStackTrace();
        }
        return contents.getRow().getDetail();
    }

}
