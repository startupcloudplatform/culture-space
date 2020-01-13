package com.crossent.microservice.admin.service;

import com.crossent.microservice.api.dto.CulturalFacilitiesDetailResponse;
import com.crossent.microservice.api.service.CulturalSpaceApiService;
import com.crossent.microservice.client.domain.CulturalFacilitiesDetail;
import com.crossent.microservice.client.domain.FacilityType;
import com.crossent.microservice.client.repository.CulturalFacilitiesDetailRepository;
import com.crossent.microservice.client.repository.FacilityTypeRepository;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CulturalSpaceAdminService {

    private static final Logger logger = LoggerFactory.getLogger(CulturalSpaceAdminService.class);

    @Autowired
    private CulturalSpaceApiService culturalSpaceApiService;

    @Autowired
    private CulturalFacilitiesDetailRepository culturalFacilitiesDetailRepository;

    @Autowired
    private FacilityTypeRepository facilityTypeRepository;

    /**
     * 문화 공간 전체 조회 API 요청
     * @return
     */
    public List<CulturalFacilitiesDetailResponse> listData(){
        return culturalSpaceApiService.listCulturalSpaceData();
    }


    /**
     *  문화 공간 데이터 저장
     */
    @Transactional
    public List<CulturalFacilitiesDetail> saveData(){

        if( culturalFacilitiesDetailRepository.findAll().size() > 0 ){
            delete();
        }

        List<CulturalFacilitiesDetail> culturalSpaces  = new ArrayList<>();
        List<FacilityType> typeCodes = new ArrayList<>();

        List<CulturalFacilitiesDetailResponse> requestData = listData();
        for( CulturalFacilitiesDetailResponse r : requestData ){

            FacilityType typeCode = new FacilityType(r.getTypeCode(), r.getTypeName());
            typeCodes.add(typeCode);

            CulturalFacilitiesDetail domain =  new CulturalFacilitiesDetail( r.getName()
                                                                            , typeCode
                                                                            , r.getPhone()
                                                                            , r.getHomepage()
                                                                            , r.getAddress()
                                                                            , r.getFee()
                                                                            , r.getxCoord()
                                                                            , r.getyCoord()
                                                                            , String.valueOf(r.getFree()));
            culturalSpaces.add(domain);
        }

        facilityTypeRepository.save(typeCodes);
        return  culturalFacilitiesDetailRepository.save(culturalSpaces);
    }

    /**
     * 문화 공간 데이터 전체 삭제
     */
    @Transactional
    public void delete(){
        culturalFacilitiesDetailRepository.deleteAll();
        facilityTypeRepository.deleteAll();

    }

    /**
     * 문화 공간 데이터 삭제 by code
     * @param code
     */
    @Transactional
    public void deleteById(float code){
        culturalFacilitiesDetailRepository.deleteByTypeCode(code);
        facilityTypeRepository.deleteByCode(code);

    }

    @Transactional
    public int uploadFile(MultipartFile file){
        int count =0;
        String fileName = file.getOriginalFilename();
        if( culturalFacilitiesDetailRepository.findAll().size() > 0 ){
            delete();
        }

        try {
            Workbook wb = null;
            if (fileName.contains(".xlsx")) {
                wb = new XSSFWorkbook(file.getInputStream());
            } else if(fileName.contains(".xls")){
                wb = new HSSFWorkbook(file.getInputStream());
            }else{
                return count;
            }
            Sheet sheet = wb.getSheetAt(0);

            int rows = sheet.getPhysicalNumberOfRows();

            for (int j = 1; j < rows; j++) {
                Row row = sheet.getRow(j);

                if (row != null) {
                    String type_name, address, enter_fee, homepage, phone, enter_free , name ;
                    type_name = address= enter_fee= homepage= phone= enter_free = name = "";
                    float x_coord, y_coord;
                    x_coord = y_coord = 0.0f;
                    float type_code = 0;

                    for( int k=0; k < row.getLastCellNum(); k++ ){
                        switch (sheet.getRow(0).getCell(k).getStringCellValue()){
                            case "장르분류코드" :
                                logger.debug(row.getCell(k).getCellType() + "");
                                logger.debug(row.getCell(k).getStringCellValue());
                                type_code = new Double(row.getCell(k).getStringCellValue()).intValue();
                                break;
                            case "장르분류명" :
                                type_name = row.getCell(k).getStringCellValue();
                                break;
                            case "문화공간명" :
                                name = row.getCell(k).getStringCellValue();
                                break;
                            case "주소"     :
                                address = row.getCell(k).getStringCellValue();
                                break;
                            case "전화번호"  :
                                phone = row.getCell(k).getStringCellValue();
                                break;
                            case "홈페이지"  :
                                homepage = row.getCell(k).getStringCellValue();
                                break;
                            case "관뢈료(원)":
                                enter_fee = row.getCell(k).getStringCellValue();
                                break;
                            case "무료구분"  :
                                enter_free = row.getCell(k).getStringCellValue();
                                break;
                            case "X좌표":
                                try{
                                    x_coord = new Double(row.getCell(k).getStringCellValue()).floatValue();
                                }catch (NumberFormatException ne){
                                    x_coord = 0.0f;
                                }
                                break;
                            case "Y좌표":
                                try{
                                    y_coord = new Double(row.getCell(k).getStringCellValue()).floatValue();
                                }catch (NumberFormatException ne){
                                    x_coord = 0.0f;
                                }
                                break;
                            default:
                                break;
                        }
                    }


                    FacilityType facilityType = facilityTypeRepository.findByCode(type_code);
                    if( facilityType == null ){
                        facilityType = facilityTypeRepository.save(new FacilityType(type_code, type_name));
                    }

                    CulturalFacilitiesDetail culturalFacilitiesDetail = new CulturalFacilitiesDetail(
                            name,
                            facilityType,
                            phone,
                            homepage,
                            address,
                            enter_fee,
                            x_coord,
                            y_coord,
                            enter_free
                    );
                    culturalFacilitiesDetailRepository.save(culturalFacilitiesDetail);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            count =culturalFacilitiesDetailRepository.listCount();
        }

        return count;

    }

}
