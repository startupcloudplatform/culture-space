package com.crossent.microservice.culturalspacefront.admin.service;

import com.crossent.microservice.culturalspacefront.portal.domain.CulturalFacilitiesDetail;
import com.crossent.microservice.culturalspacefront.portal.service.SearchService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RefreshScope
public class CulturalSpaceAdminService {

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

        return headers;
    }

    private HttpHeaders getMultipartFileHeaders(){
        HttpHeaders headers = getHeaders();
        Charset utf8 = Charset.forName("UTF-8");
        MediaType mediaType =  new MediaType(MediaType.MULTIPART_FORM_DATA, utf8);
        headers.setContentType(mediaType);

        return headers;
    }

    @PostConstruct
    private void postConstruct() {
        System.out.println(">>Basic Auth user/password: " + user + "/" + password);
    }

    //------------------------------------------------------------------------------------------------------------------------//

    /**
     * 전체 문화 공간 데이터 조회
     * @return
     */
    public List<CulturalFacilitiesDetail> listData(){

        HttpEntity<String> request = new HttpEntity<String>(getHeaders());
        URI uri = URI.create("http://apigateway/search-service/api/search/all");

        ResponseEntity<Object> result = restTemplate.exchange(uri, HttpMethod.GET, request, Object.class);
        List<CulturalFacilitiesDetail> facilityList = (List<CulturalFacilitiesDetail>)result.getBody();

        return facilityList;
    }

    /**
     * OpenAPI를 이용한 문화 공간 데이터 업데이트
     */
    public void saveData() {

        HttpEntity<String> request = new HttpEntity<String>(getHeaders());
        URI uri = URI.create("http://apigateway/search-service/api/facilities/update");

        Object result = restTemplate.postForObject(uri, request, Object.class);

    }

    /**
     * Excel 파일을 이용한 문화 공간 데이터 업데이트
     * @param file
     * @return
     */
    public int uploadFile(MultipartFile file ){

        URI uri = URI.create("http://apigateway/search-service/api/facilities/upload/file");
        int result = 0;

        try{
            UriComponents requestUri = UriComponentsBuilder.fromHttpUrl(String.valueOf(uri)).build().encode("UTF-8");
            uri = requestUri.toUri();


            LinkedMultiValueMap<String, String> headerMap = new LinkedMultiValueMap<>();
            String message  =  "form-data; name=file; filename=" + file.getOriginalFilename() +";charset=UTF-8";
            headerMap.add("Content-disposition", message);

            HttpEntity<byte[]> entity = new HttpEntity<>(file.getBytes(), headerMap);
            LinkedMultiValueMap<String, Object> multipartReqMap = new LinkedMultiValueMap<>();
            multipartReqMap.add("file", entity);

            HttpEntity<LinkedMultiValueMap<String, Object>> reqEntity = new HttpEntity<>(multipartReqMap, getMultipartFileHeaders());
            ResponseEntity<Object> response = restTemplate.exchange(uri, HttpMethod.POST, reqEntity, Object.class);
            result = (int)response.getBody();

        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

}