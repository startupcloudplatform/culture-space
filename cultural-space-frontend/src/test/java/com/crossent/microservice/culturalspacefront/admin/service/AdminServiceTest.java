package com.crossent.microservice.culturalspacefront.admin.service;

import com.crossent.microservice.culturalspacefront.CulturalSpaceFrontApplication;
import com.crossent.microservice.culturalspacefront.configuration.TestConfiguration;
import com.crossent.microservice.culturalspacefront.portal.domain.CulturalFacilitiesDetail;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CulturalSpaceFrontApplication.class)
public class AdminServiceTest {

    MockMvc mockMvc;

    @InjectMocks
    private CulturalSpaceAdminService culturalSpaceAdminService;

    @Mock
    private RestTemplate restTemplate;

    @Before
    public void setup() {

        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(culturalSpaceAdminService).build();
    }

    @Test
    public void listData() throws Exception {

        Mockito.when(restTemplate.exchange(
                Matchers.any(),
                Matchers.eq(HttpMethod.GET),
                Matchers.<HttpEntity<String>>any(),
                Matchers.any(Class.class))
        ).thenReturn(new ResponseEntity(TestConfiguration.setData(), HttpStatus.OK));

        List<CulturalFacilitiesDetail> facilityList = culturalSpaceAdminService.listData();
        assertFalse(facilityList.isEmpty());
    }

    @Test
    public void saveData() throws Exception {

        Mockito.when(restTemplate.postForObject(
                Matchers.any(),
                Matchers.<HttpEntity<String>>any(),
                Matchers.any(Class.class))
        ).thenReturn(HttpStatus.OK);

        culturalSpaceAdminService.saveData();
        assertFalse(false);
    }

    @Test
    public void fileUpload() throws Exception {

        FileInputStream fis = new FileInputStream(new File("src/test/resources/seoul_cultural_space.xlsx"));
        MockMultipartFile file = new MockMultipartFile("file","seoul_cultural_space.xlsx",
                MediaType.MULTIPART_FORM_DATA_VALUE, fis);

        Mockito.when(restTemplate.postForObject(
                Matchers.any(),
                Matchers.<LinkedMultiValueMap<String, Object>>any(),
                Matchers.any(Class.class))
        ).thenReturn(String.valueOf(TestConfiguration.setCount()));
    }
}
