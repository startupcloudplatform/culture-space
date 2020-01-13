package com.crossent.microservice.culturalspacefront.configuration;


import com.crossent.microservice.culturalspacefront.admin.domain.CulturalFacilitiesDetailResponse;
import com.crossent.microservice.culturalspacefront.portal.domain.CulturalFacilitiesDetail;
import com.crossent.microservice.culturalspacefront.portal.domain.FacilityType;
import com.crossent.microservice.culturalspacefront.portal.dto.FreeDTO;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class TestConfiguration {

    public static String QUERY_KEYWORD    = "중구청";
    public static String QUERY_NAME       = "중구청";
    public static String QUERY_TYPE       = "기타";
    public static String QUERY_ADDRESS    = "서울  중구 예관동 120-1";
    public static Float QUERY_TYPE_CODE  = 100738f;
    public static String QUERY_FREE       = "0";



    public static List<CulturalFacilitiesDetailResponse> setCulturalFacilitiesDetailData(){
        List<CulturalFacilitiesDetailResponse> list = new ArrayList<>();
        CulturalFacilitiesDetailResponse data = new CulturalFacilitiesDetailResponse();
        data.setAddress("서울  중구 예관동 120-1");
        data.setFee("");
        data.setHomepage("http://www.junggu.seoul.kr");
        data.setName("중구청");
        data.setTypeCode(100738);
        data.setPhone("02-3396-4114");
        data.setTypeName("기타");
        data.setxCoord(37.563843f);
        data.setyCoord(126.997604f);

        list.add(data);

        return list;
    }


    public static List<CulturalFacilitiesDetail> setData(){
        List<CulturalFacilitiesDetail> details = new ArrayList<>();
        FacilityType type = setType();
        CulturalFacilitiesDetail data = new CulturalFacilitiesDetail(
                 "중구청"
                , type
                , "02-3396-4114"
                , "http://www.junggu.seoul.kr"
                , "서울  중구 예관동 120-1 )"
                , ""
                , 37.563843f
                , 126.997604f
                , "0"
        );

        details.add(data);

        return details;
    }

    public static FacilityType setType(){
        return new FacilityType(100738, "기타");
    }

    public static List<FacilityType> setListType(){
        List<FacilityType> list = new ArrayList<>();
        FacilityType facilityType = new FacilityType(100738, "기타");
        list.add(facilityType);
        return list;
    }

    public static MultipartFile setFile() throws Exception{

        FileInputStream fis = new FileInputStream("src/test/resources/seoul_cultural_space.xlsx");
        MockMultipartFile file = new MockMultipartFile("file","seoul_cultural_space.xlsx","multipart/form-data",fis);

        return file;
    }

    public static long setCount(){
        return 857;
    }

    public static List<FreeDTO> setGroupFree(){
        List<FreeDTO > list = new ArrayList<>();
        FreeDTO free = new FreeDTO() {
            @Override
            public Integer getCode() {
                return 0;
            }

            @Override
            public String getName() {
                return "무료";
            }
        };
        list.add(free);
        return list;
    }

}
