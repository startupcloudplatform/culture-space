package com.crossent.microservice.client.repository;


import com.crossent.microservice.client.domain.FacilityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FacilityTypeRepository extends JpaRepository<FacilityType, Long> {

    void deleteByCode(float code);

    FacilityType findByCode(float code);

    @Query(value = "Select code, name From facility_type group by code, name", nativeQuery = true)
    List<FacilityType> listAllGroupByCodeAndName();


}
