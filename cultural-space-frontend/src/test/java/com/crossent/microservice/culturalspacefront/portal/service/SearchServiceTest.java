package com.crossent.microservice.culturalspacefront.portal.service;

import com.crossent.microservice.culturalspacefront.CulturalSpaceFrontApplication;
import com.crossent.microservice.culturalspacefront.portal.domain.CulturalFacilitiesDetail;
import com.crossent.microservice.culturalspacefront.portal.domain.FacilityType;
import com.crossent.microservice.culturalspacefront.portal.dto.FreeDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import com.crossent.microservice.culturalspacefront.configuration.TestConfiguration;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.Assert.assertFalse;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = CulturalSpaceFrontApplication.class)
public class SearchServiceTest {

    MockMvc mockMvc;

    @InjectMocks
    private SearchService searchService;

    @Mock
    private RestTemplate restTemplate;

    @Before
    public void setup() {

        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(searchService).build();
    }

    @Test
    public void listByKeyword() throws Exception {

        Mockito.when(restTemplate.exchange(
                Matchers.any(),
                Matchers.eq(HttpMethod.GET),
                Matchers.<HttpEntity<String>>any(),
                Matchers.any(Class.class))
        ).thenReturn(new ResponseEntity(TestConfiguration.setData(), HttpStatus.OK));

        List<CulturalFacilitiesDetail> facilityList = searchService.listByKeyword(TestConfiguration.QUERY_KEYWORD);
        assertFalse(facilityList.isEmpty());
    }

    @Test
    public void listByAddress() throws Exception {

        Mockito.when(restTemplate.exchange(
                Matchers.any(),
                Matchers.eq(HttpMethod.GET),
                Matchers.<HttpEntity<String>>any(),
                Matchers.any(Class.class))
        ).thenReturn(new ResponseEntity(TestConfiguration.setData(), HttpStatus.OK));

        List<CulturalFacilitiesDetail> facilityList = searchService.listByAddress(TestConfiguration.QUERY_ADDRESS);
        assertFalse(facilityList.isEmpty());

    }

    @Test
    public void listWithCondition() throws Exception {

        Mockito.when(restTemplate.exchange(
                Matchers.any(),
                Matchers.eq(HttpMethod.GET),
                Matchers.<HttpEntity<String>>any(),
                Matchers.any(Class.class))
        ).thenReturn(new ResponseEntity(TestConfiguration.setData(), HttpStatus.OK));

        List<CulturalFacilitiesDetail> facilityList = searchService.listWithCondition(TestConfiguration.QUERY_TYPE_CODE,
                                            TestConfiguration.QUERY_FREE, TestConfiguration.QUERY_ADDRESS);
        assertFalse(facilityList.isEmpty());
    }

    @Test
    public void listGroupByType() throws Exception {

        Mockito.when(restTemplate.exchange(
                Matchers.any(),
                Matchers.eq(HttpMethod.GET),
                Matchers.<HttpEntity<String>>any(),
                Matchers.any(Class.class))
        ).thenReturn(new ResponseEntity(TestConfiguration.setData(), HttpStatus.OK));

        List<FacilityType> facilityList = searchService.listGroupByType();
        assertFalse(facilityList.isEmpty());

    }

    @Test
    public void listGroupByFree() throws Exception {

        Mockito.when(restTemplate.exchange(
                Matchers.any(),
                Matchers.eq(HttpMethod.GET),
                Matchers.<HttpEntity<String>>any(),
                Matchers.any(Class.class))
        ).thenReturn(new ResponseEntity(TestConfiguration.setData(), HttpStatus.OK));

        List<FreeDTO> facilityList = searchService.listGroupByFree();
        assertFalse(facilityList.isEmpty());

    }

}