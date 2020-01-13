package com.crossent.microservice.client.repository;


import com.crossent.microservice.client.domain.CulturalFacilitiesDetail;
import com.crossent.microservice.client.dto.FreeDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CulturalFacilitiesDetailRepository extends JpaRepository<CulturalFacilitiesDetail, Long> {

    List<CulturalFacilitiesDetail>findByNameLikeOrderByName(String name);
    List<CulturalFacilitiesDetail>findByTypeNameLikeOrderByTypeName(String type);
    List<CulturalFacilitiesDetail>findByTypeCodeOrderByTypeCode(float code);
    List<CulturalFacilitiesDetail>findByAddressLikeOrderByAddress(String address);
    List<CulturalFacilitiesDetail>findByFreeOrderByName(String free);
    List<CulturalFacilitiesDetail>findByFreeIsNullOrderByName();
    void deleteByTypeCode(float code);

    @Query(value = "Select * From cultural_facilities_detail cfd Inner Join facility_type ft on cfd.code = ft.code " +
                            "AND ( Case When :code is null then ft.code > 0  When :code = 0.0 then ft.code > 0  Else ft.code = :code End )" +
                            "AND ( :free    is null OR free    LIKE CONCAT('%', :free, '%') )" +
                            "AND ( :address is null OR address LIKE CONCAT('%', :address, '%') )", nativeQuery = true)
    List<CulturalFacilitiesDetail> findByTypeCodeLikeAndFreeAndAddressLike(@Param("code") Float code, @Param("free") String free, @Param("address") String address);


    @Query(value = " Select * From cultural_facilities_detail Where name Like CONCAT('%', :keyword, '%')  UNION  " +
                    "Select id, d.name, address, enter_fee, free, homepage, phone, x_coord, y_coord, d.code From cultural_facilities_detail d INNER JOIN facility_type ft ON d.code = ft.code AND ft.name Like CONCAT('%', :keyword, '%')  UNION " +
                    "Select * From cultural_facilities_detail Where address Like CONCAT('%', :keyword, '%') ", nativeQuery = true)
    List<CulturalFacilitiesDetail> findByNameAndTypeAndAddress(@Param("keyword") String keyword);


    @Query(value = "Select IF(free is null, -1, free)  as 'code', IF(free = '0' , '무료', IF(free is null, '모름','유료') ) as 'name' From cultural_facilities_detail group by free; ", nativeQuery = true)
    List<FreeDTO> listGroupByFree();

    @Query(value = "Select count(*) From cultural_facilities_detail", nativeQuery = true)
    int listCount();

}
