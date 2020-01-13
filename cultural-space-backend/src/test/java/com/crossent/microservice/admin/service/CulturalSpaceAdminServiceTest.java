package com.crossent.microservice.admin.service;

import com.crossent.microservice.MicroserviceBackendApplication;
import com.crossent.microservice.api.dto.CulturalFacilitiesDetailResponse;
import com.crossent.microservice.api.service.CulturalSpaceApiService;
import com.crossent.microservice.configuration.TestConfiguration;
import com.crossent.microservice.client.domain.FacilityType;
import com.crossent.microservice.client.repository.CulturalFacilitiesDetailRepository;
import com.crossent.microservice.client.repository.FacilityTypeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MicroserviceBackendApplication.class)
public class CulturalSpaceAdminServiceTest {

    MockMvc mockMvc;

    @InjectMocks
    private CulturalSpaceAdminService culturalSpaceAdminService;

    @Mock
    private CulturalSpaceApiService culturalSpaceApiService;

    @Mock
    private FacilityTypeRepository facilityTypeRepository;

    @Mock
    private CulturalFacilitiesDetailRepository culturalFacilitiesDetailRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(culturalSpaceAdminService).build();
    }

    @Test
    public void listData() throws Exception {
        when(culturalSpaceApiService.listCulturalSpaceData()).thenReturn(TestConfiguration.setCulturalFacilitiesDetailData());

        List<CulturalFacilitiesDetailResponse> list = culturalSpaceAdminService.listData();
        assertFalse(list.isEmpty());
    }

    @Test
    public void saveData() throws Exception {
        FacilityType facilityType = TestConfiguration.setType();

        when(culturalSpaceApiService.listCulturalSpaceData()).thenReturn(TestConfiguration.setCulturalFacilitiesDetailData());
        when(culturalSpaceAdminService.listData()).thenReturn(TestConfiguration.setCulturalFacilitiesDetailData());

        when(facilityTypeRepository.save(facilityType)).thenReturn(facilityType);
        when(culturalFacilitiesDetailRepository.save(TestConfiguration.setData())).thenReturn(TestConfiguration.setData());

        culturalSpaceAdminService.saveData();
    }

    @Test
    public void delete() throws Exception {
        doNothing().when(facilityTypeRepository).deleteAll();
        culturalSpaceAdminService.delete();
    }

    @Test
    public void deleteById() throws Exception {
        float code = 3.0f;
        doNothing().when(facilityTypeRepository).deleteByCode(code);
        doNothing().when(culturalFacilitiesDetailRepository).deleteByTypeCode(code);

        culturalSpaceAdminService.deleteById(code);
    }
}
