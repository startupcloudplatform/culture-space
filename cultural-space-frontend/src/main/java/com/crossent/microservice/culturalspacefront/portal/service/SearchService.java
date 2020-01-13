package com.crossent.microservice.culturalspacefront.portal.service;

import com.crossent.microservice.culturalspacefront.portal.domain.CulturalFacilitiesDetail;
import com.crossent.microservice.culturalspacefront.portal.domain.FacilityType;
import com.crossent.microservice.culturalspacefront.portal.dto.FreeDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.*;

@Service
@RefreshScope
public class SearchService {
    private static final Logger logger = LoggerFactory.getLogger(SearchService.class);

    @Autowired
    @LoadBalanced
    private RestTemplate restTemplate;

    @Value("${gateway.basic.user:}")
    String user;

    @Value("${gateway.basic.password:}")
    String password;

    // spring security가 적용되어 있기 때문에 http 요청 시, header정보를 반드시 함께 전달해야함
    private HttpHeaders getHeaders(){
        String basicAuth = String.format("%s:%s", user, password);
        String base64Auth = Base64Utils.encodeToString(basicAuth.getBytes());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", String.format("Basic %s", base64Auth));
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		postConstruct();

        return headers;
    }

    @PostConstruct
    private void postConstruct() {
        System.out.println(">>Basic Auth user/password: " + user + "/" + password);
    }

    //------------------------------------------------------------------------------------------------------------------------//

    /**
     * 키워드를 통한 문화 공간 데이터 조회 API
     * @param keyword
     * @return
     */
    public List<CulturalFacilitiesDetail> listByKeyword(String keyword){

        HttpEntity<String> request = new HttpEntity<String>(getHeaders());
        URI uri = URI.create("http://apigateway/search-service/api/search/name/list");

        try{
            UriComponents requestUri = UriComponentsBuilder.fromHttpUrl(String.valueOf(uri)).
                    queryParam("query", keyword).build().encode("UTF-8");
            uri = requestUri.toUri();
        }catch(Exception e){
            e.printStackTrace();
        }

        ResponseEntity<Object> result = restTemplate.exchange(uri, HttpMethod.GET, request, Object.class);
        List<CulturalFacilitiesDetail> facilityList = (List<CulturalFacilitiesDetail>)result.getBody();

        return facilityList;
    }

    /**
     * 주소 검색을 통한 문화 공간 데이터 조회 API
     * @param address
     * @return
     */
    public List<CulturalFacilitiesDetail> listByAddress(String address){

        HttpEntity<String> request = new HttpEntity<String>(getHeaders());
        URI uri = URI.create("http://apigateway/search-service/api/search/address/list");

        try{
            UriComponents requestUri = UriComponentsBuilder.fromHttpUrl(String.valueOf(uri)).
                    queryParam("query", address).build().encode("UTF-8");
            uri = requestUri.toUri();
        }catch(Exception e){
            e.printStackTrace();
        }

        ResponseEntity<Object> result = restTemplate.exchange(uri, HttpMethod.GET, request, Object.class);
        List<CulturalFacilitiesDetail> facilityList = (List<CulturalFacilitiesDetail>)result.getBody();

        return facilityList;
    }

    /**
     * 분류명, 입장 요금, 주소 등 조건 검색을 통한 데이터 조회 API
     * @param code
     * @param free
     * @param address
     * @return
     */
    public List<CulturalFacilitiesDetail> listWithCondition(float code, String free, String address){
        HttpEntity<String> request = new HttpEntity<String>(getHeaders());
        URI uri = URI.create("http://apigateway/search-service/api/search/condition");

        try{
            UriComponents requestUri = UriComponentsBuilder.fromHttpUrl(String.valueOf(uri)).
                    queryParam("type", code).
                    queryParam("free",free).
                    queryParam("address",address).build().encode("UTF-8");
            uri = requestUri.toUri();
        }catch(Exception e){
            e.printStackTrace();
        }

        ResponseEntity<Object> result = restTemplate.exchange(uri, HttpMethod.GET, request, Object.class);
        List<CulturalFacilitiesDetail> facilityList = (List<CulturalFacilitiesDetail>)result.getBody();
        return facilityList;
    }

    /**
     * 문화 공간 시설 분류명 그룹 조회 API
     * @return
     */
    public List<FacilityType> listGroupByType(){


        HttpEntity<String> request = new HttpEntity<String>(getHeaders());
        URI uri = URI.create("http://apigateway/search-service/api/search/type/group");

        ResponseEntity<Object> result = restTemplate.exchange(uri, HttpMethod.GET, request, Object.class);
        List<FacilityType> facilityList = (List<FacilityType>)result.getBody();

        return facilityList;
    }

    /**
     * 문화 공간 입장 요금 그룹 조회 API
     * @return
     */
    public List<FreeDTO> listGroupByFree(){

        HttpEntity<String> request = new HttpEntity<String>(getHeaders());
        URI uri = URI.create("http://apigateway/search-service/api/search/fee/group");

        ResponseEntity<Object> result = restTemplate.exchange(uri, HttpMethod.GET, request, Object.class);
        List<FreeDTO> facilityList = (List<FreeDTO>)result.getBody();

        return facilityList;
    }

}
