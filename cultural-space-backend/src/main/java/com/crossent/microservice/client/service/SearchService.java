package com.crossent.microservice.client.service;

import com.crossent.microservice.client.domain.CulturalFacilitiesDetail;
import com.crossent.microservice.client.domain.FacilityType;
import com.crossent.microservice.client.dto.FreeDTO;
import com.crossent.microservice.client.repository.CulturalFacilitiesDetailRepository;
import com.crossent.microservice.client.repository.FacilityTypeRepository;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {

    private static final Logger logger = LoggerFactory.getLogger(SearchService.class);

    @Autowired
    private CulturalFacilitiesDetailRepository culturalFacilitiesDetailRepository;

    @Autowired
    private FacilityTypeRepository facilityTypeRepository;


    /**
     * 문화 공간 전체 조회
     * @return
     */
    public List<CulturalFacilitiesDetail> listAll(){
        return culturalFacilitiesDetailRepository.findAll();
    }

    /**
     * 문화 공간 전체 개수 조회
     * @return
     */
    public long getCountsSpaceAll(){
        return culturalFacilitiesDetailRepository.count();
    }

    /**
     * 키워드를 통한 목록 조회
     * @param keyword
     * @return
     */
    public List<CulturalFacilitiesDetail> listByKeyword(String keyword){
        return culturalFacilitiesDetailRepository.findByNameAndTypeAndAddress(keyword);
    }

    /**
     * 문화 공간 명 키워드를 통한 데이터 조회 API
     * @param name
     * @return
     */
    public List<CulturalFacilitiesDetail> listByName(String name){
        return culturalFacilitiesDetailRepository.findByNameLikeOrderByName("%"+name+"%");
    }

    /**
     * 시설 분류 명 키워드를 통한 데이터 조회 API
     * @param type
     * @return
     */
    public List<CulturalFacilitiesDetail> listByFacilityTypeName(String type){
        return culturalFacilitiesDetailRepository.findByTypeNameLikeOrderByTypeName("%"+type+"%");
    }

    /**
     * 시설 분류 코드 키워드를 통한 데이터 조회 API
     * @param code
     * @return
     */
    public List<CulturalFacilitiesDetail> listByFacilityTypeCode(float code){
        return culturalFacilitiesDetailRepository.findByTypeCodeOrderByTypeCode(code);
    }

    /**
     * 주소 키워드를 통한 데이터 조회 API
     * @param address
     * @return
     */
    public List<CulturalFacilitiesDetail> listByAddress(String address){
        return culturalFacilitiesDetailRepository.findByAddressLikeOrderByAddress("%"+address+"%");
    }

    /**
     * 무료/유료 키워드를 통한 데이터 조회 API
     * @param free
     * @return
     */
    public List<CulturalFacilitiesDetail> listByFee(String free){
        List<CulturalFacilitiesDetail> list = null;
        if( free != null ){
            list = culturalFacilitiesDetailRepository.findByFreeOrderByName(free);
        }else{
            list = culturalFacilitiesDetailRepository.findByFreeIsNullOrderByName();
        }
        return list;
    }

    /**
     * 장르 분류명, 입장 요금, 주소 등 조건 검색을 통한 데이터 조회 API
     * @param code
     * @param free
     * @param address
     * @return
     */
    public List<CulturalFacilitiesDetail> listWithCondition(Float code, String free, String address){
        String enterFree = StringUtils.isEmpty(free) ? null : free;
        String addr =  StringUtils.isEmpty(address) ? null : address;
        return culturalFacilitiesDetailRepository.findByTypeCodeLikeAndFreeAndAddressLike(code, enterFree, addr);
    }

    /**
     * 문화 공간 시설 분류명 그룹 조회 API
     * @return
     */
    public List<FacilityType> listGroupByType(){
        return facilityTypeRepository.listAllGroupByCodeAndName();
    }

    /**
     * 문화 공간 입장 요금 그룹 조회 API
     * @return
     */
    public List<FreeDTO> listGroupByFree(){
        List<FreeDTO> list = culturalFacilitiesDetailRepository.listGroupByFree();

        logger.debug( list.toString()) ;
        return list;
    }


}
